package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;
@TeleOp(name = "starterbot")
public class starterbot extends OpMode {
    Gamepad curr1 = null;
    Gamepad prev1 = null;

    private DcMotorEx leftMotor;
    private DcMotorEx rightMotor;

    private DcMotorEx intake;

    private DcMotorEx flywheel;

    private ServoImplEx block;

    private boolean intakeToggle = false;
    private boolean flywheelToggle = false;

    private ElapsedTime mRuntime;

    private double integralSum = 0;
    private double lastError = 0;
    private ElapsedTime pidTimer = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    private static final double TICKS_PER_REV = 537.7; // Đây là con số cho motor Gobilda 5203 series
    public static double Kp = 0.1;
    public static double Ki = 0.0;
    public static double Kd = 0.0001;
    public static double Kf = 0.5;
    public static double TARGET_VELOCITY = 1500;

    @Override
    public void init(){
        prev1 = new Gamepad();
        curr1 = new Gamepad();

        intake = hardwareMap.get(DcMotorEx.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheel.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        block = hardwareMap.get(ServoImplEx.class, "block");
        block.setPosition(0);

        leftMotor = hardwareMap.get(DcMotorEx.class, "leftMotor");
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        rightMotor = hardwareMap.get(DcMotorEx.class, "rightMotor");
        rightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        mRuntime = new ElapsedTime();
    }

    @Override
    public void loop() {
        prev1.copy(curr1);
        curr1.copy(gamepad1);

        double translationalPower = -gamepad1.left_stick_y;
        double rotationalPower = gamepad1.right_stick_x * 0.67;

        double leftSideOutput = translationalPower + rotationalPower;
        double rightSideOutput = translationalPower - rotationalPower;

        double max = Math.max(Math.abs(leftSideOutput), Math.abs(rightSideOutput));
        if (max > 1.0) {
            leftSideOutput /= max;
            rightSideOutput /= max;
        }

        leftMotor.setPower(leftSideOutput);
        rightMotor.setPower(rightSideOutput);

        if(curr1.left_bumper && !prev1.left_bumper) flywheelToggle = !flywheelToggle;

        if (curr1.right_bumper && !prev1.right_bumper) intakeToggle = ! intakeToggle;

        intake.setPower((intakeToggle) ? 1 : 0);

        if (curr1.right_trigger > 0.2) {
            intake.setPower(-1);
        }
        else if (curr1.left_trigger < 0.2) {
            intake.setPower(0);
        }

        if (curr1.circle && !prev1.circle) {
            mRuntime.reset();
        }

        if (flywheelToggle) {
            double targetTicksPerSecond = (TARGET_VELOCITY * TICKS_PER_REV) / 60.0;

            double power = PIDF(targetTicksPerSecond) * 0.67;
            flywheel.setPower(power);
        } else {
            flywheel.setPower(0);
            integralSum = 0;
            lastError = 0;
            pidTimer.reset();
        }

        if (mRuntime.seconds() < 5.0) {
            block.setPosition(0.4);
        } else {
            block.setPosition(0);
        }
        double currentTicksPerSecond = flywheel.getVelocity();
        double currentRPM = (currentTicksPerSecond * 60.0) / TICKS_PER_REV;

        telemetry.addData("Target RPM", TARGET_VELOCITY);
        telemetry.addData("Current TPS", "%.2f", currentRPM); 
        telemetry.update();
    }
    public double PIDF(double targetVelocity) {
        if (pidTimer.time() == 0) pidTimer.reset();

        double currentVelocity = flywheel.getVelocity();
        double error = targetVelocity - currentVelocity;

        if(pidTimer.time() < 0.00001) {
            return (Kp * error) + (Kf * targetVelocity);
        }
        integralSum = integralSum + (error * pidTimer.time());

        double derivative = (error - lastError) / pidTimer.time();

        lastError = error;
        pidTimer.reset();

        double outputPower = (Kp * error) + (Ki * integralSum) + (Kd * derivative) + (Kf * targetVelocity);

        return outputPower;
    }

}
