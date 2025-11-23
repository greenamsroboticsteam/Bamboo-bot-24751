package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@TeleOp(name = "AutoOp")
public class AutoOp extends OpMode {
    private DcMotor leftMotor, rightMotor;
    private Telemetry telemetry;
    private static final double TICKS_PER_MOTOR_REV = 537.7; // Ví dụ cho motor goBILDA 5203 series
    private static final double DRIVE_GEAR_REDUCTION = 1.0;   // Không có hộp số phụ
    private static final double WHEEL_DIAMETER_METERS = 0.096; //nên tự đo thông số này


    @Override
    public void init() {
        this.leftMotor = hardwareMap.get(DcMotor.class, "left_motor");
        this.rightMotor = hardwareMap.get(DcMotor.class, "right_motor");

        this.leftMotor.setDirection(DcMotor.Direction.FORWARD);
        this.rightMotor.setDirection(DcMotor.Direction.REVERSE); //change during test

        this.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        this.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void loop() {
        drive(0.5, 2.0);
    }

    public void drive(double power, double distance) {
        double TICKS_PER_METER = TICKS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION
                / (WHEEL_DIAMETER_METERS * Math.PI);
        int targetTicks = (int) (distance * TICKS_PER_METER);
        this.leftMotor.setTargetPosition(targetTicks);
        this.rightMotor.setTargetPosition(targetTicks);
        this.leftMotor.setPower(power);
        this.rightMotor.setPower(power);

        this.telemetry.addData("Status", "Running to position");
        this.telemetry.addData("Target Postion", distance);
        this.telemetry.addData("Left Motor", "At %distance", this.leftMotor.getCurrentPosition());
        this.telemetry.addData("Right Motor", "At %distance", this.rightMotor.getCurrentPosition());
        this.telemetry.update();

        this.leftMotor.setPower(0.0);
        this.rightMotor.setPower(0.0);
        

    }
}
