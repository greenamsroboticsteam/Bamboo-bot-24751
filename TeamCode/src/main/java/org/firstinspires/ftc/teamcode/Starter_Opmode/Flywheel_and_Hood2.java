package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Flywheel and Hood (Independent)2")
public class Flywheel_and_Hood2 extends OpMode {
    private DcMotor flywheel;
    private Servo servo;
    private double flywheelPower = 0.0;
    private double hoodPosition = 0.5;

    private final double POWER_INCREMENT = 0.05;
    private final double POSITION_INCREMENT = 0.03;

    private boolean dpadUpWasPressed = false;
    private boolean dpadDownWasPressed = false;
    private boolean aWasPressed = false;
    private boolean bWasPressed = false;

    @Override
    public void init() {
        flywheel = hardwareMap.get(DcMotor.class, "flywheel");
        servo = hardwareMap.get(Servo.class, "servo");

        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        flywheel.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.addData(">", "Press Start to run");
        telemetry.update();
    }

    @Override
    public void start() {
        flywheel.setPower(flywheelPower);
        servo.setPosition(hoodPosition);

        telemetry.addData("Status", "Running");
        telemetry.update();
    }

    @Override
    public void loop() {

        boolean dpadUpIsPressed = gamepad1.dpad_up;
        boolean dpadDownIsPressed = gamepad1.dpad_down;
        boolean aIsPressed = gamepad1.a;
        boolean bIsPressed = gamepad1.b;

        // --- Điều khiển Flywheel: Chỉ tăng/giảm khi nút VỪA ĐƯỢC NHẤN ---
        // Điều kiện: nút đang được nhấn VÀ vòng lặp trước đó nó CHƯA được nhấn.
        if (dpadUpIsPressed && !dpadUpWasPressed) {
            flywheelPower += POWER_INCREMENT;
        } else if (dpadDownIsPressed && !dpadDownWasPressed) {
            flywheelPower -= POWER_INCREMENT;
        }

        if(aIsPressed && !aWasPressed) {
            hoodPosition += POSITION_INCREMENT;
        } else if (bIsPressed && !bWasPressed) {
            hoodPosition -= POSITION_INCREMENT;
        }

        dpadUpWasPressed = dpadUpIsPressed;
        dpadDownWasPressed = dpadDownIsPressed;
        aWasPressed = aIsPressed;
        bWasPressed = bIsPressed;

        flywheelPower = Range.clip(flywheelPower, 0.0, 1.0);
        hoodPosition = Range.clip(hoodPosition, 0.0, 1.0);

        flywheel.setPower(flywheelPower);
        servo.setPosition(hoodPosition);

        // Telemetry giữ nguyên
        telemetry.addData("--- Controls ---", "");
        telemetry.addData("Flywheel", "D-Pad Up/Down");
        telemetry.addData("Hood", "A/B Buttons");
        telemetry.addData("--- Status ---", "");
        telemetry.addData("Flywheel Power", "%.2f", flywheelPower);
        telemetry.addData("Hood Position", "%.2f", hoodPosition);
        telemetry.update();
    }

    @Override
    public void stop() {
        flywheel.setPower(0.0);
        servo.setPosition(0.5);

        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }
}
