package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.apache.commons.math3.*;

@TeleOp(name = "1DKF", group = "base")
public class OneDimentionalKalmanFilter extends LinearOpMode {
    TypexChart chart = new TypexChart();

    public OneDimentionalKalmanFilter() throws Exception {
        Logging.setup();
        Logging.log("Starting Data Collection Logging for 1DKF.");
    }

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();
    }
}
