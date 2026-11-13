package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.subsystem.Flywheel;
@Configurable
@TeleOp(name = "starterbot")
public class starterbot extends OpMode {
    Gamepad curr1 = null;
    Gamepad prev1 = null;

    private DcMotorEx leftMotor;
    private DcMotorEx rightMotor;

    private DcMotorEx intake;

    // Flywheel with PIDF velocity control (see subsystem/Flywheel.java).
    private final Flywheel flywheel = new Flywheel();

    private ServoImplEx block;
    private ServoImplEx block2;

    private boolean intakeToggle = false;
    @Override
    public void init() {
        prev1 = new Gamepad();
        curr1 = new Gamepad();

        intake = hardwareMap.get(DcMotorEx.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        flywheel.init(hardwareMap, telemetry);

        block = hardwareMap.get(ServoImplEx.class, "leftBlock");
        block.setPosition(0);

        block2 = hardwareMap.get(ServoImplEx.class, "rightBlock");
        block2.setPosition(1.0);

        leftMotor = hardwareMap.get(DcMotorEx.class, "leftMotor");
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        rightMotor = hardwareMap.get(DcMotorEx.class, "rightMotor");
        rightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void start() {
        flywheel.start();
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

        // dpad up / down: change flywheel target speed by Flywheel.RPM_STEP
        flywheel.shoot(curr1.dpad_up && !prev1.dpad_up, curr1.dpad_down && !prev1.dpad_down);
        if(curr1.left_bumper && !prev1.left_bumper) {
            flywheel.toggle();
        }
        if (curr1.right_bumper && !prev1.right_bumper) intakeToggle = !intakeToggle;

        if (curr1.x) {
            intakeToggle = false;
            intake.setPower(0.5);
        } else if (curr1.right_trigger > 0.2) {
            intakeToggle = false;
            intake.setPower(-1);
        } else if (intakeToggle) {
            intake.setPower(0.7);
        } else intake.setPower(0.0);

        // Runs the PIDF once per loop: reads the encoder and sets the flywheel power.
        flywheel.update();

        if (curr1.x) {
            block.setPosition(0.4);
            block2.setPosition(0.6);
        } else {
            block.setPosition(0);
            block2.setPosition(1.0);
        }

        flywheel.periodic();
        telemetry.addData("Servo Position: ", block.getPosition());
        telemetry.update();
    }

    @Override
    public void stop() {
        flywheel.stop();
    }
}
