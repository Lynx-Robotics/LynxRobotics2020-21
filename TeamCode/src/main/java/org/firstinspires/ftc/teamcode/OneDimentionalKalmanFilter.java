package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.apache.commons.math3.*;

import java.io.IOException;

@TeleOp(name = "1DKF", group = "base")
public class OneDimentionalKalmanFilter extends LinearOpMode {
    TypexChart chart = new TypexChart();
    public boolean kalmanFilterThreadRunning = false;
    public boolean kalmanFilterStopThread = false;

    public void KalmanFilterThread() {
        if (!kalmanFilterThreadRunning) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    kalmanFilterThreadRunning = true;

                    while (!kalmanFilterStopThread && opModeIsActive()) {
                        telemetry.addData("Test?", null);
                        telemetry.update();
                        sleep(1000);
                        telemetry.addData("Working?", null);
                        telemetry.update();
                    }

                    kalmanFilterThreadRunning = false;
                    kalmanFilterStopThread = false;
                }
            }).start();
        }
    }

    public OneDimentionalKalmanFilter() throws Exception {
        Logging.setup();
        Logging.log("Starting Data Collection Logging for 1DKF.");
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

            if (gamepad1.a) {
                chart.TL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                chart.TR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                chart.TL.setTargetPosition(1120);
                chart.TR.setTargetPosition(1120);
                chart.TL.setPower(.5);
                chart.TR.setPower(.5);
                chart.TL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                chart.TR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
