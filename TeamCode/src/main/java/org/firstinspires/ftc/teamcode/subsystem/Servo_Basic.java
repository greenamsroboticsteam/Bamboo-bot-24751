package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Servo_Basic {
    private Servo servo;
    private double position;
    private Telemetry telemetry;
    private final double HOOD_INCREMENT = 0.05;//bien tang goc cua hood

    public void init(HardwareMap hwMap, Telemetry telemetry) {
        this.servo = hwMap.get(Servo.class, "servo");//remember to change servo's name

        this.telemetry = telemetry;

        this.position = 0.5; //bắt đầu ở vị trí 0
        this.servo.setPosition(this.position);
    }

    public void setPosition(boolean increase, boolean decrease) {
        if (increase) {
            this.position += HOOD_INCREMENT;
        }
        if (decrease) {
            this.position -= HOOD_INCREMENT;
        }

        this.position = Math.max(0.0, Math.min(1.0, this.position));

        this.servo.setPosition(this.position);
    }


    public void periodic() {
        if (telemetry != null) {
            telemetry.addData("Hood Commanded Position", "%.2f", getNormalizedPosition());
            telemetry.addData("Hood Angle (degrees)", "%.2f", getAngle());
        }
    }
    public double getAngle() {
        return this.servo.getPosition() * 300.0; // 300.0 là góc quay tối đa của servo goBilda
    }

    public double getNormalizedPosition() {
        return this.position;
    }
}
