package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Servo_Basic {
    private Servo servo;
    private double position;
    private Telemetry telemetry;
    private final double HOOD_INCREMENT = 0.05;//bien tang goc cua hood

    public void init(HardwareMap hwMap, Telemetry telemetry) {
        this.servo = hwMap.get(Servo.class, "servo");//remember to change servo's name
        this.telemetry = telemetry;

    }
    public void start() {
        this.position = 0.5;
        this.servo.setPosition(this.position);
    }

    public void setPosition(boolean increase, boolean decrease) {
        if (increase) {
            this.position += HOOD_INCREMENT;
        }
        if (decrease) {
            this.position -= HOOD_INCREMENT;
        }

        this.position = Range.clip(this.position, 0.0, 1.0);
        this.servo.setPosition(this.position);
    }

    public void setPosition(double position) {
        this.position = position;
        this.position = Range.clip(this.position, 0.0, 1.0);
        this.servo.setPosition(this.position);
    }

    public void periodic() {
        if (telemetry != null) {
            telemetry.addData("Hood Commanded Position", "%.2f", getNormalizedPosition());
            telemetry.addData("Hood Angle (degrees)", "%.2f", getAngle());
        }
    }
    public double getAngle() {
        return this.servo.getPosition() * 300.0;
    }

    public double getNormalizedPosition() {
        return this.position;
    }
}

