package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystem.Flywheel;

/**
 * Tuning OpMode for the flywheel PIDF. Put the robot somewhere safe; no balls loaded.
 *
 * STEP 1 — measure max speed (finds kF)
 *   Hold RIGHT BUMPER: flywheel runs at full power with no PIDF.
 *   Wait until "Max RPM seen" stops rising, then press B to copy the suggested kF.
 *
 * STEP 2 — tune with PIDF on
 *   LEFT BUMPER       : flywheel on / off
 *   DPAD UP / DOWN    : target RPM + / -
 *   DPAD LEFT / RIGHT : choose which gain to change (kP, kI, kD, kF)
 *   Y / A             : selected gain x1.25 / x0.8
 *
 *   Suggested order: kF first (actual RPM should land close to target with kP = 0),
 *   then raise kP until the speed recovers quickly after a drop without oscillating.
 *   Add a little kI only if a small steady error remains. kD is usually 0 for flywheels.
 *
 * STEP 3 — write the final numbers shown on screen into Flywheel.java.
 *   The values changed here stay in memory until the Robot Controller app restarts,
 *   so you can switch to starterbot right away to test, but they are not saved.
 */
@TeleOp(name = "Flywheel PIDF Tuner", group = "Tuning")
public class Flywheel_PIDF_Tuner extends OpMode {
    private final Flywheel flywheel = new Flywheel();
    private final Gamepad curr = new Gamepad();
    private final Gamepad prev = new Gamepad();

    private static final String[] GAIN_NAMES = {"kP", "kI", "kD", "kF"};
    // Starting value used when a gain is 0 and you press Y (x1.25 of 0 would stay 0).
    private static final double[] GAIN_SEEDS = {0.0001, 0.00002, 0.000002, 1.0 / 6000.0};
    private int selectedGain = 0;

    private double maxRPMSeen = 0.0;
    private double voltageAtMax = 12.0;

    @Override
    public void init() {
        flywheel.init(hardwareMap, telemetry);
        telemetry.addData("Status", "Initialized. Hold RIGHT BUMPER after START to measure max speed.");
        telemetry.update();
    }

    @Override
    public void start() {
        flywheel.start();
    }

    @Override
    public void loop() {
        prev.copy(curr);
        curr.copy(gamepad1);

        boolean measuring = curr.right_bumper;

        if (measuring) {
            flywheel.setRawPower(1.0);
            double rpm = flywheel.getVelocityRPM();
            if (rpm > maxRPMSeen) {
                maxRPMSeen = rpm;
                voltageAtMax = flywheel.getBatteryVoltage();
            }
        } else {
            if (pressed(curr.left_bumper, prev.left_bumper)) flywheel.toggle();
            if (pressed(curr.dpad_up, prev.dpad_up)) flywheel.adjustTargetRPM(Flywheel.RPM_STEP);
            if (pressed(curr.dpad_down, prev.dpad_down)) flywheel.adjustTargetRPM(-Flywheel.RPM_STEP);
            if (pressed(curr.dpad_right, prev.dpad_right)) selectedGain = (selectedGain + 1) % 4;
            if (pressed(curr.dpad_left, prev.dpad_left)) selectedGain = (selectedGain + 3) % 4;
            if (pressed(curr.y, prev.y)) scaleSelectedGain(1.25);
            if (pressed(curr.a, prev.a)) scaleSelectedGain(0.8);
            flywheel.update();
        }

        // kF measured at the current battery voltage, converted to the nominal voltage.
        double suggestedKF = 0.0;
        if (maxRPMSeen > 0.0) {
            suggestedKF = voltageAtMax / (Flywheel.NOMINAL_VOLTAGE * maxRPMSeen);
            if (pressed(curr.b, prev.b)) Flywheel.kF = suggestedKF;
        }

        telemetry.addData("Mode", measuring ? "MEASURING MAX SPEED (full power)" : "PIDF");
        flywheel.periodic();
        telemetry.addData("Error RPM", "%.0f", flywheel.getTargetRPM() - flywheel.getVelocityRPM());
        telemetry.addData("Battery", "%.2f V", flywheel.getBatteryVoltage());
        telemetry.addLine();
        telemetry.addData("Max RPM seen", "%.0f", maxRPMSeen);
        telemetry.addData("Suggested kF (press B)", "%.7f", suggestedKF);
        telemetry.addLine();
        telemetry.addData("Selected gain", GAIN_NAMES[selectedGain]);
        telemetry.addData("kP", "%.7f", Flywheel.kP);
        telemetry.addData("kI", "%.7f", Flywheel.kI);
        telemetry.addData("kD", "%.7f", Flywheel.kD);
        telemetry.addData("kF", "%.7f", Flywheel.kF);

        // Encoder sanity check: with real power applied the wheel must read a positive speed.
        if (flywheel.getPower() > 0.3) {
            if (flywheel.getVelocityRPM() < -50) {
                telemetry.addLine("WARNING: speed is negative. Encoder direction is reversed — stop and fix before using PIDF.");
            } else if (Math.abs(flywheel.getVelocityRPM()) < 50) {
                telemetry.addLine("WARNING: speed reads ~0. Is the encoder cable plugged in?");
            }
        }
        telemetry.update();
    }

    @Override
    public void stop() {
        flywheel.stop();
    }

    private void scaleSelectedGain(double factor) {
        double value = getGain(selectedGain);
        if (value == 0.0 && factor > 1.0) {
            value = GAIN_SEEDS[selectedGain];
        } else {
            value *= factor;
        }
        setGain(selectedGain, value);
    }

    private static double getGain(int i) {
        switch (i) {
            case 0: return Flywheel.kP;
            case 1: return Flywheel.kI;
            case 2: return Flywheel.kD;
            default: return Flywheel.kF;
        }
    }

    private static void setGain(int i, double value) {
        switch (i) {
            case 0: Flywheel.kP = value; break;
            case 1: Flywheel.kI = value; break;
            case 2: Flywheel.kD = value; break;
            default: Flywheel.kF = value; break;
        }
    }

    private static boolean pressed(boolean now, boolean before) {
        return now && !before;
    }
}
