package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Motor_Basic;
@TeleOp(name = "Motor Test")
public class Motor_Test extends OpMode {
    Motor_Basic motor;
    private final double CONSTANT_VELOCITY = 0.5;

    @Override
    public void init() {
        this.motor = new Motor_Basic();
        this.motor.init(this.hardwareMap, this.telemetry);
        this.telemetry.addData("state", "init");
        this.telemetry.update();
    }

    @Override
    public void loop() {
        this.motor.setPower(CONSTANT_VELOCITY);
        this.motor.periodic();
        this.telemetry.update();
    }
    @Override
    public void stop() {
        this.motor.stop();
    }
}


