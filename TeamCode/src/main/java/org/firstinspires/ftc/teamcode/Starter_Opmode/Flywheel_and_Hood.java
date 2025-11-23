package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.subsystem.Flywheel;
import org.firstinspires.ftc.teamcode.subsystem.Servo_Basic;

@TeleOp(name="Flywheel and Hood (Independent)")
public class Flywheel_and_Hood extends OpMode {
    private Flywheel flywheel;
    private Servo_Basic servo;
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
        this.servo = new Servo_Basic();
        this.flywheel = new Flywheel();
        this.flywheel.init(this.hardwareMap, this.telemetry);
        this.servo.init(this.hardwareMap, this.telemetry);

        this.telemetry.addData("Status", "Initialized");
        this.telemetry.addData(">", "Press Start to run");
        this.telemetry.update();
    }
    @Override
    public void start() {
        this.flywheel.start();
        this.servo.start();
    }

    @Override
    public void loop() {
        this.isFlywheelIncreaseIsPressed = this.gamepad1.dpad_up;
        this.isFlywheelDecreaseIsPressed = this.gamepad1.dpad_down;

        boolean FlywheelFutureIncrease = false;
        boolean FlywheelFutureDecrease = false;

        if (this.isFlywheelIncreaseIsPressed && !this.flywheelIncreaseWasPressed) {
            FlywheelFutureIncrease = true;
        } else if(this.isFlywheelDecreaseIsPressed && !this.flywheelDecreaseWasPressed) {
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

        this.servo.setPosition(HoodFutureIncrease, HoodFutureDecrease);
        this.flywheel.shoot(FlywheelFutureIncrease, FlywheelFutureDecrease);

        telemetry.addData("--- Controls ---", "");
        telemetry.addData("Flywheel", "D-Pad Up/Down");
        telemetry.addData("Hood", "A/B Buttons");
        telemetry.addData("--- Status ---", "");
        telemetry.addData("Flywheel Power", "%.2f", this.flywheel.getPower());
        telemetry.addData("Hood Position", "%.2f", this.servo.getAngle());
        telemetry.update();
    }

    @Override
    public void stop() {
        this.flywheel.stop();
        this.servo.setPosition(0.5);

        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }
}
