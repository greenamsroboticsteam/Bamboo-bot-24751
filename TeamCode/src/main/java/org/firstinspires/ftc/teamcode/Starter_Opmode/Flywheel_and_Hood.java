package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Flywheel;
import org.firstinspires.ftc.teamcode.subsystem.Hood;
@TeleOp(name="Flywheel_and_Hood")
public class Flywheel_and_Hood extends OpMode {
    private Flywheel flywheel;
    private Hood hood;


    @Override
    public void init() {
        this.flywheel.init(this.hardwareMap, this.telemetry);
        this.hood.init(this.hardwareMap, this.telemetry);
    }

    @Override
    public void loop() {
        this.flywheel.shoot(gamepad1.dpad_up, gamepad1.dpad_down);
        this.hood.setPosition(gamepad1.a, gamepad1.b);

        this.flywheel.periodic();
        this.hood.periodic();
        this.telemetry.update();
    }

    @Override
    public void stop() {
        this.flywheel.stop();
    }
}
