package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Drivebase {
    private DcMotor rightMotor, leftMotor;
    private final double DRIFT_THERSH = 0.02;
    HardwareMap hwMap;
    Telemetry telemetry;
    public void init(HardwareMap hwMap, Telemetry telemetry) {
        this.hwMap = hwMap;
        this.telemetry = telemetry;

        this.rightMotor = this.hwMap.get(DcMotor.class, "right_motor");// khai báo ten motor
        this.rightMotor.setDirection(DcMotor.Direction.REVERSE); //neu di nguoc doi lai thanh FORWARD
        this.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.leftMotor = this.hwMap.get(DcMotor.class, "left_motor");
        this.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.leftMotor.setDirection(DcMotor.Direction.REVERSE);
        this.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void periodic() {
        telemetry.addData("Right Motor Power", this.rightMotor.getPower());
        telemetry.addData("Left Motor Power", this.leftMotor.getPower());
    }
    public void stop() {
        this.rightMotor.setPower(0);
        this.leftMotor.setPower(0);
    }
    private double deadzone(double value) {
        return Math.abs(value) < this.DRIFT_THERSH ? 0 : value;
    }
    public void drive(double x, double y) { //rotate, drive
        double _x = this.deadzone(x);
        double _y = this.deadzone(-y);

        double maximum = Math.max(Math.abs(_x), Math.abs(_y));
        double total = _x + _y;
        double difference = _y - _x;
        if (x >= 0 && y >= 0) { // phan tu thu I
            this.rightMotor.setPower(difference);
            this.leftMotor.setPower(maximum);
        }
        if (y >= 0 && x < 0) { // phan tu thu II
            this.rightMotor.setPower(maximum);
            this.leftMotor.setPower(total);
        }
        if (x >= 0 && y < 0) { //phan tu thu IV
            this.rightMotor.setPower(-maximum);
            this.leftMotor.setPower(total);
        }
        if (x < 0 && y < 0) { //phan tu thu III
            this.rightMotor.setPower(-difference);
            this.leftMotor.setPower(-maximum);
        }
    }



}
