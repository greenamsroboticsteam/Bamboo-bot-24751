package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Motor_Basic;
@TeleOp(name = "Motor Test")
public class Motor_Test extends OpMode {
    Motor_Basic motor;

    @Override
    public void init() {
        this.motor.init(this.hardwareMap, this.telemetry);
        telemetry.addData("state", "init");
        telemetry.update();
    }

    @Override
    public void loop() {
        this.motor.setPower(gamepad1.dpad_up, gamepad1.dpad_down);
        this.motor.periodic();
        this.telemetry.update();
    }
    @Override
    public void stop() {
        this.motor.stop();
    }
}
