package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Flywheel with closed-loop velocity control (PIDF).
 *
 * Instead of sending a fixed power, we choose a target speed in RPM. Every loop the
 * encoder measures the real speed and the PIDF controller adjusts the power to hold
 * the target, even when the battery drains or a ball slows the wheel down.
 *
 * IMPORTANT: call update() once in every loop of your OpMode, otherwise the power is
 * never recalculated.
 *
 * The flywheel motor's encoder cable MUST be plugged into the Control Hub.
 */
public class Flywheel {

    // ---------------- Settings to check for your robot ----------------

    /**
     * Encoder ticks per revolution of the motor shaft (before any gearbox).
     * 28 is correct for goBILDA Yellow Jacket and REV HD Hex bare motors.
     * If this is wrong, the RPM numbers are scaled wrong, but control still works
     * as long as kF is measured with the tuner.
     */
    public static double TICKS_PER_REV = 28.0;

    /** Starting target when the flywheel is turned on (RPM). Tune on the field. */
    public static double DEFAULT_TARGET_RPM = 3000.0;

    /** How much one dpad press changes the target (RPM). */
    public static double RPM_STEP = 100.0;

    /** Highest target allowed (RPM). Set a little under the measured max speed. */
    public static double MAX_TARGET_RPM = 6000.0;

    /** The flywheel counts as "at speed" when within this many RPM of the target. */
    public static double READY_TOLERANCE_RPM = 75.0;

    // ---------------- PIDF gains (units: motor power per RPM) ----------------
    // Find these with the "Flywheel PIDF Tuner" OpMode, then write the values here.
    // kF ≈ 1 / (max RPM at full power on a ~12 V battery).
    public static double kP = 0.0005;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static double kF = 1.0 / 6000.0;

    /** Battery voltage that kF was measured at. Used to scale kF when the battery is lower. */
    public static double NOMINAL_VOLTAGE = 12.0;

    // -----------------------------------------------------------------

    private DcMotorEx motor;
    private VoltageSensor voltageSensor;
    private Telemetry telemetry;

    private final PIDFController controller = new PIDFController(kP, kI, kD, kF);

    private boolean enabled = false;
    private double targetRPM = DEFAULT_TARGET_RPM;
    private double lastPower = 0.0;
    private double lastVelocityRPM = 0.0;

    // Reading the battery voltage is slow, so we only refresh it a few times per second.
    private final ElapsedTime voltageTimer = new ElapsedTime();
    private double cachedVoltage = 12.0;

    public void init(HardwareMap hwMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.motor = hwMap.get(DcMotorEx.class, "flywheel");
        this.motor.setDirection(DcMotorSimple.Direction.REVERSE);
        // We run our own PIDF, so the hub's built-in velocity loop is off.
        // getVelocity() still reads the encoder in this mode.
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        this.voltageSensor = hwMap.voltageSensor.iterator().next();
        this.cachedVoltage = voltageSensor.getVoltage();
        this.voltageTimer.reset();
    }

    public void start() {
        enabled = false;
        targetRPM = DEFAULT_TARGET_RPM;
        controller.reset();
        motor.setPower(0.0);
    }

    /** Must be called once every loop. Reads the encoder and sets the motor power. */
    public void update() {
        lastVelocityRPM = ticksPerSecondToRPM(motor.getVelocity());

        if (!enabled || targetRPM <= 0.0) {
            // Let the wheel coast down. Never use negative power to brake a flywheel.
            lastPower = 0.0;
            controller.reset();
        } else {
            // Pick up any gain changes (for example from the tuner OpMode).
            controller.setCoefficients(kP, kI, kD, kF);
            double voltageScale = NOMINAL_VOLTAGE / getBatteryVoltage();
            double output = controller.calculate(targetRPM, lastVelocityRPM, voltageScale);
            // A flywheel only spins one way, so power stays between 0 and 1.
            lastPower = Range.clip(output, 0.0, 1.0);
        }
        motor.setPower(lastPower);
    }

    /** Kept for compatibility: dpad-style increase/decrease of the target speed. */
    public void shoot(boolean increase, boolean decrease) {
        if (increase) adjustTargetRPM(RPM_STEP);
        if (decrease) adjustTargetRPM(-RPM_STEP);
    }

    public void adjustTargetRPM(double delta) {
        setTargetRPM(targetRPM + delta);
    }

    public void setTargetRPM(double rpm) {
        targetRPM = Range.clip(rpm, 0.0, MAX_TARGET_RPM);
    }

    public double getTargetRPM() {
        return targetRPM;
    }

    public void setEnabled(boolean on) {
        if (on && !enabled) controller.reset();
        enabled = on;
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public boolean isEnabled() {
        return enabled;
    }

    /** True when the wheel is on and close enough to the target speed to shoot. */
    public boolean isAtSpeed() {
        return enabled && Math.abs(targetRPM - lastVelocityRPM) <= READY_TOLERANCE_RPM;
    }

    public double getVelocityRPM() {
        return lastVelocityRPM;
    }

    public double getPower() {
        return lastPower;
    }

    public double getBatteryVoltage() {
        if (voltageTimer.milliseconds() > 250) {
            double v = voltageSensor.getVoltage();
            if (v > 1.0) cachedVoltage = v; // ignore bad readings
            voltageTimer.reset();
        }
        return cachedVoltage;
    }

    /** Sends the motor raw power with no PIDF. Used by the tuner to measure max speed. */
    public void setRawPower(double power) {
        enabled = false;
        controller.reset();
        lastPower = Range.clip(power, 0.0, 1.0);
        motor.setPower(lastPower);
        lastVelocityRPM = ticksPerSecondToRPM(motor.getVelocity());
    }

    public void periodic() {
        telemetry.addData("Flywheel", enabled ? (isAtSpeed() ? "READY" : "spinning up") : "off");
        telemetry.addData("Flywheel Target RPM", "%.0f", targetRPM);
        telemetry.addData("Flywheel Actual RPM", "%.0f", lastVelocityRPM);
        telemetry.addData("Flywheel Power", "%.2f", lastPower);
    }

    public void stop() {
        enabled = false;
        lastPower = 0.0;
        motor.setPower(0.0);
    }

    private static double ticksPerSecondToRPM(double ticksPerSecond) {
        return ticksPerSecond / TICKS_PER_REV * 60.0;
    }
}
