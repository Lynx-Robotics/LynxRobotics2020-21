package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.apache.commons.math3.analysis.function.Gaussian;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.io.IOException;
import java.security.Guard;
import java.util.concurrent.TimeUnit;

@TeleOp(name = "DataCollection", group = "base")
public class DataCollection extends LinearOpMode {
    TypexChart chart = new TypexChart();

    public DataCollection() throws Exception {
        Logging.setup();
        Logging.log("Starting Data Collection Logging for DataCollection.");
    }

    @Override
    public void runOpMode() throws InterruptedException {
        chart.init(hardwareMap);
        Gaussian g1 = new Gaussian(10,2);
        Gaussian g2 = new Gaussian(12,4);


        AlgorithmLibrary lib = null;
        try {
            lib = new AlgorithmLibrary();
        } catch (IOException e) {
            e.printStackTrace();
        }

        chart.TL.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        chart.TR.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        chart.BL.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        chart.BR.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        waitForStart();
        chart.runtime.reset();

        while (opModeIsActive()) {
            lib.imuDataRecordThread();

            double range = chart.sensorRange.getDistance(DistanceUnit.INCH);

//            chart.TL.setPower(gamepad1.left_stick_y);
//            chart.BL.setPower(gamepad1.left_stick_y);
//            chart.TR.setPower(gamepad1.right_stick_y);
//            chart.BR.setPower(gamepad1.right_stick_y);

            chart.TL.setPower(-1);
            chart.TR.setPower(-1);
            chart.BL.setPower(-1);
            chart.BR.setPower(-1);
            if (chart.runtime.time(TimeUnit.SECONDS) > 3) {
                lib.imuDataStopThread = true;
                requestOpModeStop();
            }
            telemetry.addData("range", String.format("%.01f in", range));
            telemetry.addData("range", String.format("%.01f avg", lib.avgValue(range)));
            Logging.log("Range: " + range);
            Logging.log("Encoders: " + chart.TL.getCurrentPosition() + " " + chart.TR.getCurrentPosition() + " " + chart.BL.getCurrentPosition() + " " + chart.BR.getCurrentPosition());

            telemetry.update();
        }
    }

}
