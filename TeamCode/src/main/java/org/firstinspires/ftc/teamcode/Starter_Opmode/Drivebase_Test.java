package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Drivebase;

@TeleOp(name="Drivebase_Test")
public class Drivebase_Test extends OpMode {
    Drivebase drivebase;

    @Override
    public void init() {
        this.drivebase = new Drivebase();
        this.drivebase.init(this.hardwareMap, this.telemetry);
    }

    @Override
    public void loop() {
        this.drivebase.drive(this.gamepad1.left_stick_x, this.gamepad1.right_stick_y);
        this.drivebase.periodic();
        this.telemetry.update();
    }

    @Override
    public void stop() {
        this.drivebase.stop();
    }
}
