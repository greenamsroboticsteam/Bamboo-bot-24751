package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
@Autonomous(name = "AutoOp")
public class AutoOp extends LinearOpMode {
    private DcMotor leftMotor, rightMotor;
    private static final double TICKS_PER_MOTOR_REV = 537.7; // Ví dụ cho motor goBILDA 5203 series
    private static final double DRIVE_GEAR_REDUCTION = 1.0;   // Không có hộp số phụ
    private static final double WHEEL_DIAMETER_METERS = 0.096; //nên tự đo thông số này
    @Override
    public void runOpMode() {
        this.leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        this.rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");

        this.leftMotor.setDirection(DcMotor.Direction.FORWARD);
        this.rightMotor.setDirection(DcMotor.Direction.REVERSE); //change during test

        this.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData(">", "Robot is initialized and ready.");
        telemetry.addData(">", "Press Play to start.");
        telemetry.update();
        waitForStart();
        if(isStopRequested()) return;

        if(opModeIsActive())
            drive(0.5, 1.0);
    }

    public void drive(double power, double distance) {
        double TICKS_PER_METER = TICKS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION
                / (WHEEL_DIAMETER_METERS * Math.PI);
        int targetTicks = (int) (distance * TICKS_PER_METER);
        this.leftMotor.setTargetPosition(targetTicks);
        this.rightMotor.setTargetPosition(targetTicks);
        this.leftMotor.setPower(power);
        this.rightMotor.setPower(power);

        while(opModeIsActive() && this.leftMotor.isBusy() && this.rightMotor.isBusy()) {
            this.telemetry.addData("Status", "Running to position");
            this.telemetry.addData("Target Postion", distance);
            this.telemetry.addData("Left Motor", "At %distance", this.leftMotor.getCurrentPosition());
            this.telemetry.addData("Right Motor", "At %distance", this.rightMotor.getCurrentPosition());
            this.telemetry.update();
            idle();
        }

        this.leftMotor.setPower(0.0);
        this.rightMotor.setPower(0.0);
        
        this.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
