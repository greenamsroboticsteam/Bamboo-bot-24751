package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Drivebase;
import org.firstinspires.ftc.teamcode.subsystem.Flywheel;
import org.firstinspires.ftc.teamcode.subsystem.Hood;

@TeleOp(name="TeleopTest")
public class TeleopTest extends OpMode {
    Drivebase drivebase;
    Flywheel flywheel;
    Hood hood;

    @Override
    public void init() {
        this.drivebase = new Drivebase();
        this.drivebase.init(this.hardwareMap, this.telemetry);

        this.flywheel = new Flywheel();
        this.flywheel.init(this.hardwareMap, this.telemetry);

        this.hood = new Hood();
        this.hood.init(this.hardwareMap, this.telemetry);

        telemetry.addData("state", "init");
        telemetry.update();
    }
    @Override
    public void loop() {
        telemetry.addData("state", "loop");

        this.drivebase.drive(this.gamepad1.left_stick_x, this.gamepad1.right_stick_y);
        //this.drivebase.drive(this.gamepad1.left_stick_x, this.gamepad1.left_stick_y);

        this.flywheel.shoot(this.gamepad1.dpad_up, this.gamepad1.dpad_down); //thay doi tuy y
        this.hood.setPosition(this.gamepad1.a, this.gamepad1.b); //thay doi tuy y

        this.drivebase.periodic();
        this.flywheel.periodic();
        this.hood.periodic();
        telemetry.update();
    }

    @Override
    public void stop() {
        this.drivebase.stop();
        this.flywheel.stop();

        telemetry.addData("state", "stop");
    }
}
