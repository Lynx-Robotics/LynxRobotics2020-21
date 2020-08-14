package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.apache.commons.math3.*;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.io.IOException;
import java.util.Arrays;

@TeleOp(name = "1DKF", group = "base")
public class OneDimentionalKalmanFilter extends LinearOpMode {
    public OneDimentionalKalmanFilter() throws Exception {
        Logging.setup();
        Logging.log("Starting Data Collection Logging for 1DKF.");
    }
    TypexChart chart = new TypexChart();
    GaussianLibrary gLib = new GaussianLibrary();
    AlgorithmLibrary lib = new AlgorithmLibrary();
    public boolean kalmanFilterThreadRunning = false;
    public boolean kalmanFilterStopThread = false;

    double process_var = 1;
    double sensor_var = 0.5;

    public double[] x = gLib.gaussian(0, 20);
    //velocity returns in in/sec
//    public double velocity = ((chart.BL.getVelocity() + chart.BR.getVelocity()) / 2)*lib.ticksToIN;
    public double velocity;
    double[] process_model = gLib.gaussian(velocity, process_var);
    public double kalmanRange;

    public void KalmanFilterThread() {
        if (!kalmanFilterThreadRunning) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    kalmanFilterThreadRunning = true;
                    int i = 0;
                    while (!kalmanFilterStopThread && opModeIsActive()) {
                        //gLib.zs.add((chart.BL.getCurrentPosition() + chart.BR.getCurrentPosition()) / 2 * lib.ticksToIN);
                        gLib.zs.add(chart.sensorRange.getDistance(DistanceUnit.INCH));

                        double[] prior = gLib.predict(x, process_model);
                        double[] likelihood = gLib.gaussian((Double) gLib.zs.get(i), sensor_var);
                        i++;
                        x = gLib.update(prior, likelihood);
                        Logging.log("Prior: " + Arrays.toString(prior));
                        Logging.log("Update: " + Arrays.toString(x));
//                        telemetry.addData("Prior: ", Arrays.toString(prior));
//                        telemetry.addData("Update: ", Arrays.toString(x));
//                        telemetry.update();
                        kalmanRange = gLib.mean(x);
                    }

                    kalmanFilterThreadRunning = false;
                    kalmanFilterStopThread = false;
                }
            }).start();
        }
    }



    @Override
    public void runOpMode() throws InterruptedException {
        chart.init(hardwareMap);

        AlgorithmLibrary lib = null;
        try {
            lib = new AlgorithmLibrary();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GaussianLibrary gLib = new GaussianLibrary();

        waitForStart();
        KalmanFilterThread();

        while (opModeIsActive()) {
//            telemetry.addData("Encoders: ", chart.BL.getCurrentPosition());
//            telemetry.addData("Encoders: ", chart.BR.getCurrentPosition());
            velocity = ((chart.BL.getVelocity() + chart.BR.getVelocity()) / 2)*lib.ticksToIN;
            telemetry.addData("Velocity: ", velocity);
            telemetry.addData("Kalman Range: ", kalmanRange);
            telemetry.addData("Sensor Range: ", chart.sensorRange.getDistance(DistanceUnit.INCH));
            telemetry.update();
            if (gamepad1.dpad_up) {
                chart.BL.setTargetPosition(1120);
                chart.BR.setTargetPosition(1120);
                chart.BL.setPower(.5);
                chart.BR.setPower(.5);
                chart.BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                chart.BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad1.dpad_down) {
                chart.BL.setTargetPosition(-1120);
                chart.BR.setTargetPosition(-1120);
                chart.BL.setPower(-0.5);
                chart.BR.setPower(-0.5);
                chart.BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                chart.BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if (gamepad1.a) {
                chart.BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                chart.BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                chart.BL.setPower(0);
                chart.BR.setPower(0);
            }
            if (gamepad1.b) {
                kalmanFilterStopThread = false;
                KalmanFilterThread();
            }
            if (gamepad1.x) {
                kalmanFilterStopThread = true;
            }
        }
    }

}
