package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Drivebase_2 {
    private DcMotor rightMotor, leftMotor;
    private Telemetry telemetry;

    // Hằng số để tránh "magic numbers"
    private final double DRIFT_THRESHOLD = 0.05;

    public void init(HardwareMap hwMap, Telemetry telemetry) {
        // Lưu lại telemetry để có thể sử dụng trong các hàm khác như periodic() và drive()
        this.telemetry = telemetry;

        // Khởi tạo động cơ
        rightMotor = hwMap.get(DcMotor.class, "right_motor");
        leftMotor = hwMap.get(DcMotor.class, "left_motor");

        // Cấu hình chiều quay (thường là một bên FORWARD, một bên REVERSE)
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        // Cấu hình chế độ hoạt động
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Cấu hình chế độ dừng (BRAKE giúp robot dừng ngay lập tức)
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Hàm này nên được gọi trong vòng lặp của OpMode để hiển thị thông tin
    public void periodic() {
        // Telemetry đã được chuyển vào hàm drive() để có thông tin chi tiết hơn
    }

    // Dừng robot
    public void stop() {
        rightMotor.setPower(0);
        leftMotor.setPower(0);
    }

    // Hàm deadzone để loại bỏ nhiễu joystick
    private double deadzone(double value) {
        return Math.abs(value) < DRIFT_THRESHOLD ? 0 : value;
    }

    /**
     * Điều khiển robot theo kiểu Arcade Drive.
     * @param x Giá trị từ joystick trái theo trục X (dùng để rẽ).
     * @param y Giá trị từ joystick trái theo trục Y (dùng để tiến/lùi).
     */
    public void drive(double x, double y) {
        // Áp dụng deadzone
        double drive = deadzone(-y); // Joystick Y bị đảo ngược
        double turn  = deadzone(x);

        // Tính toán công suất cho mỗi bên
        double leftPower  = drive + turn;
        double rightPower = drive - turn;

        // Chuẩn hóa công suất để không vượt quá [-1.0, 1.0]
        double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));
        if (max > 1.0) {
            leftPower /= max;
            rightPower /= max;
        }

        // Gán công suất cho động cơ
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);

        // Cung cấp thông tin gỡ lỗi chi tiết
        telemetry.addData("Input Y", "%.2f", y);
        telemetry.addData("Input X", "%.2f", x);
        telemetry.addData("---", "---");
        telemetry.addData("Left Power", "%.2f", leftPower);
        telemetry.addData("Right Power", "%.2f", rightPower);
    }
}
