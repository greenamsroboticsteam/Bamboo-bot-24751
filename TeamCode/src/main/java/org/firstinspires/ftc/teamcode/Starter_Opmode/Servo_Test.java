package org.firstinspires.ftc.teamcode.Starter_Opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;import org.firstinspires.ftc.teamcode.subsystem.Servo_Basic;

@TeleOp(name = "Servo Test")
public class Servo_Test extends OpMode {
    Servo_Basic servo;


    @Override
    public void init() {
        this.servo = new Servo_Basic();
        // Giả sử hàm init này tồn tại trong Servo_Basic và hoạt động đúng
        this.servo.init(this.hardwareMap, this.telemetry, "turret1");
        this.telemetry.addData("state", "init");
        this.telemetry.update();
    }

    @Override
    public void start() {
        this.telemetry.addData("Servo's Position: ", this.servo.getNormalizedPosition());
        this.telemetry.addData("state", "start");
        this.telemetry.update();
    }

    @Override
    public void loop() {
        boolean SorterIsPressed = gamepad1.a;
        if(SorterIsPressed) this.servo.setPosition(1.0);
        else this.servo.setPosition(0.0);

        this.servo.periodic();
        this.telemetry.addData("state", "loop");
        this.telemetry.addLine("Sorter: A");
        this.telemetry.update();
    }

    @Override
    public void stop() {
        this.servo.stop();
        this.telemetry.addData("state", "stop");
        this.telemetry.update();
    }
}
