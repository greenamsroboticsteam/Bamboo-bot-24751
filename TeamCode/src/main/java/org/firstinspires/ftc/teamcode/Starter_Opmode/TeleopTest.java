package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Drivebase;
import org.firstinspires.ftc.teamcode.subsystem.Drivebase_2;
import org.firstinspires.ftc.teamcode.subsystem.Flywheel;
import org.firstinspires.ftc.teamcode.subsystem.Motor_Basic;
import org.firstinspires.ftc.teamcode.subsystem.Servo_Basic;

@TeleOp(name="TeleopTest")
public class TeleopTest extends OpMode {
    Drivebase_2 drivebase;
    Flywheel flywheel;
    Motor_Basic intake;
    Motor_Basic Runningup;
    Servo_Basic servo_sorter;
    private boolean flywheelIncreaseWasPressed = false;
    private boolean flywheelDecreaseWasPressed = false;
    private boolean isFlywheelIncreaseIsPressed;
    private boolean isFlywheelDecreaseIsPressed;
    private boolean IntakeIsPressed = gamepad1.left_bumper;
    private boolean RunningupIsPressed = gamepad1.right_bumper;
    private boolean SorterIsPressed = gamepad1.a;


    @Override
    public void init() {
        this.drivebase = new Drivebase_2();
        this.drivebase.init(this.hardwareMap, this.telemetry);

        this.flywheel = new Flywheel();
        this.flywheel.init(this.hardwareMap, this.telemetry, "flywheel");

        this.intake = new Motor_Basic();
        this.intake.init(this.hardwareMap, this.telemetry, "intake");

        this.Runningup = new Motor_Basic();
        this.Runningup.init(this.hardwareMap, this.telemetry, "Runningup");

        this.servo_sorter = new Servo_Basic();
        this.servo_sorter.init(this.hardwareMap, this.telemetry, "servo_sorter");

        this.telemetry.addData("state", "init");
        this.telemetry.update();
    }
    @Override
    public void start() {
        this.servo_sorter.start();
        this.intake.start();
        this.Runningup.start();
        this.flywheel.start();

        this.telemetry.addData("state", "start");
        this.telemetry.update();
    }
    @Override
    public void loop() {
        this.isFlywheelIncreaseIsPressed = this.gamepad1.dpad_up;
        this.isFlywheelDecreaseIsPressed = this.gamepad1.dpad_down;

        boolean FlywheelFutureIncrease = false;
        boolean FlywheelFutureDecrease = false;

        if (this.isFlywheelIncreaseIsPressed && !this.flywheelIncreaseWasPressed) {
            FlywheelFutureIncrease = true;
        }
        if(this.isFlywheelDecreaseIsPressed && !this.flywheelDecreaseWasPressed) {
            FlywheelFutureDecrease = true;
        }
        if(this.IntakeIsPressed) this.intake.setPower(1.0);

        if(this.RunningupIsPressed) this.Runningup.setPower(1.0);

        if(this.SorterIsPressed) this.servo_sorter.setPosition(1.0);
        else this.servo_sorter.setPosition(0.0);

        this.flywheelIncreaseWasPressed = this.isFlywheelIncreaseIsPressed;
        this.flywheelDecreaseWasPressed = this.isFlywheelDecreaseIsPressed;

        this.drivebase.drive(this.gamepad1.left_stick_x, this.gamepad1.right_stick_y);

        this.flywheel.shoot(FlywheelFutureIncrease, FlywheelFutureDecrease);

        this.telemetry.addLine("Status: Running");
        this.telemetry.addLine("Increase flywheel power: D-Pad Up");
        this.telemetry.addLine("Decrease flywheel power: D-Pad Down");
        this.telemetry.addLine("Intake: Left Bumper");
        this.telemetry.addLine("Runningup: Right Bumper");
        this.telemetry.addLine("Sorter: A");

        this.flywheel.periodic();
        this.telemetry.update();
    }

    @Override
    public void stop() {
        this.drivebase.stop();
        this.flywheel.stop();
        this.intake.stop();
        this.Runningup.stop();

        this.telemetry.addData("state", "stop");
    }
}
