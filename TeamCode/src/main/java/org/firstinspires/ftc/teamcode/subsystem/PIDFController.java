package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * A simple PIDF controller.
 *
 * output = kF * target                 (feedforward: the power we expect the target to need)
 *        + kP * error                  (proportional: push harder the further we are from target)
 *        + kI * sum(error * dt)        (integral: fixes a small error that stays for a long time)
 *        - kD * d(measured)/dt         (derivative: damps fast changes)
 *
 * error = target - measured
 *
 * The derivative is taken on the measurement, not on the error, so changing the
 * target does not cause a sudden spike in output.
 */
public class PIDFController {
    private double kP, kI, kD, kF;

    /** Largest absolute value the I term may add to the output (stops "integral windup"). */
    private double integralLimit = 0.25;

    private double integralSum = 0.0;
    private double lastMeasured = 0.0;
    private double lastError = 0.0;
    private boolean firstRun = true;
    private final ElapsedTime timer = new ElapsedTime();

    public PIDFController(double kP, double kI, double kD, double kF) {
        setCoefficients(kP, kI, kD, kF);
    }

    public void setCoefficients(double kP, double kI, double kD, double kF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }

    public void setIntegralLimit(double limit) {
        this.integralLimit = Math.abs(limit);
    }

    /** Clears the stored integral and derivative history. Call when the target jumps or the mechanism restarts. */
    public void reset() {
        integralSum = 0.0;
        lastError = 0.0;
        firstRun = true;
        timer.reset();
    }

    /**
     * Computes the output for one loop.
     *
     * @param target       the value we want (for example a velocity)
     * @param measured     the value the sensor reads right now
     * @param feedforwardScale multiplier applied to the F term only (used for battery voltage compensation; pass 1.0 for none)
     */
    public double calculate(double target, double measured, double feedforwardScale) {
        double error = target - measured;

        double dt = timer.seconds();
        timer.reset();

        double derivative = 0.0;
        if (firstRun || dt <= 0.0) {
            firstRun = false;
        } else {
            integralSum += error * dt;
            derivative = (measured - lastMeasured) / dt;
        }

        // Keep the I term inside +/- integralLimit.
        if (kI != 0.0) {
            double maxSum = integralLimit / Math.abs(kI);
            integralSum = Math.max(-maxSum, Math.min(maxSum, integralSum));
        }
        // When the error changes sign we have crossed the target: drop the old integral
        // so it does not keep pushing us past it.
        if (Math.signum(error) != Math.signum(lastError)) {
            integralSum = 0.0;
        }

        lastMeasured = measured;
        lastError = error;

        return kF * target * feedforwardScale
                + kP * error
                + kI * integralSum
                - kD * derivative;
    }

    public double calculate(double target, double measured) {
        return calculate(target, measured, 1.0);
    }

    public double getLastError() {
        return lastError;
    }
}
