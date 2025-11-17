package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Flywheel implements Subsystem {
    private DcMotor motor;
    private Telemetry telemetry;
    private final double VELOCITY_INCREMENT = 0.05;//bien tang giam toc do flywheel
    private double power;

    @Override
    public void init(HardwareMap hwMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.motor = hwMap.get(DcMotor.class, "flywheel");
        this.motor.setDirection(DcMotor.Direction.REVERSE);
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.power = 0.0;
        this.motor.setPower(this.power);
    }
    public void shoot(boolean increase, boolean decrease) {
        if (increase) {
            power += VELOCITY_INCREMENT;
        }
        if (decrease) {
            power -= VELOCITY_INCREMENT;
        }
        if (power > 1.0) {
            power = 1.0;
        }
        if (power < 0.0) {
            power = 0.0;
        }
        this.motor.setPower(power);
    }
    public double getPower() {
        return this.power;
    }

    @Override
    public void periodic() {
        telemetry.addData("Flywheel Target Power", "%.2f", getPower());
        telemetry.addData("Flywheel Actual Power", "%.2f", this.motor.getPower());
    }
    
    @Override
    public void stop() {
        this.power = 0.0;
        this.motor.setPower(this.power);
    }
}
