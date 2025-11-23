package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Drivebase;
import org.firstinspires.ftc.teamcode.subsystem.Drivebase_2;
import org.firstinspires.ftc.teamcode.subsystem.Flywheel;
import org.firstinspires.ftc.teamcode.subsystem.Servo_Basic;

@TeleOp(name="TeleopTest")
public class TeleopTest extends OpMode {
    Drivebase_2 drivebase;
    Flywheel flywheel;
    Servo_Basic servoBasic;
    private boolean flywheelIncreaseWasPressed = false;
    private boolean flywheelDecreaseWasPressed = false;
    private boolean isFlywheelIncreaseIsPressed;
    private boolean isFlywheelDecreaseIsPressed;

    private boolean HoodIncreaseWasPressed = false;
    private boolean HoodDecreaseWasPressed = false;
    private boolean HoodIncreaseIsPressed;
    private boolean HoodDecreaseIsPressed;


    @Override
    public void init() {
        this.drivebase = new Drivebase_2();
        this.drivebase.init(this.hardwareMap, this.telemetry);

        this.flywheel = new Flywheel();
        this.flywheel.init(this.hardwareMap, this.telemetry);

        this.servoBasic = new Servo_Basic();
        this.servoBasic.init(this.hardwareMap, this.telemetry);

        this.telemetry.addData("state", "init");
        this.telemetry.update();
    }
    @Override
    public void start() {
        this.flywheel.start();
        this.servoBasic.start();
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
        this.HoodDecreaseIsPressed = this.gamepad1.b;
        this.HoodIncreaseIsPressed = this.gamepad1.a;

        boolean HoodFutureIncrease = false;
        boolean HoodFutureDecrease = false;

        if (this.HoodIncreaseIsPressed && !this.HoodIncreaseWasPressed) {
            HoodFutureIncrease = true;
        } else if(this.HoodDecreaseIsPressed && !this.HoodDecreaseWasPressed) {
            HoodFutureDecrease = true;
        }

        this.flywheelIncreaseWasPressed = this.isFlywheelIncreaseIsPressed;
        this.flywheelDecreaseWasPressed = this.isFlywheelDecreaseIsPressed;

        this.HoodIncreaseWasPressed = this.HoodIncreaseIsPressed;
        this.HoodDecreaseWasPressed = this.HoodDecreaseIsPressed;

        this.servoBasic.setPosition(HoodFutureIncrease, HoodFutureDecrease);

        this.drivebase.drive(this.gamepad1.left_stick_x, this.gamepad1.right_stick_y);
        //this.drivebase.drive(this.gamepad1.left_stick_x, this.gamepad1.left_stick_y);

        this.flywheel.shoot(FlywheelFutureIncrease, FlywheelFutureDecrease);

        this.drivebase.periodic();
        this.flywheel.periodic();
        this.servoBasic.periodic();
        this.telemetry.update();
    }

    @Override
    public void stop() {
        this.drivebase.stop();
        this.flywheel.stop();

        this.telemetry.addData("state", "stop");
    }
}
