package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp(name = "BasicTele", group = "base")
public class BasicTele extends LinearOpMode {
    TypexChart chart = new TypexChart();


    @Override
    public void runOpMode() throws InterruptedException {

        chart.init(hardwareMap);

        AlgorithmLibrary lib = new AlgorithmLibrary();

        IMUDataThread thread1 = new IMUDataThread();

        waitForStart();
        chart.runtime.reset();

        thread1.start();

        try {
            while (opModeIsActive()) {
                chart.TL.setPower(gamepad1.left_stick_y);
                chart.BL.setPower(gamepad1.left_stick_y);
                chart.TR.setPower(gamepad1.right_stick_y);
                chart.BR.setPower(gamepad1.right_stick_y);

                double range = chart.sensorRange.getDistance(DistanceUnit.INCH);
                telemetry.addData("range", String.format("%.01f in", range));
                telemetry.addData("range", String.format("%.01f avg", lib.avgValue(range)));
                telemetry.addData("heading ", lib.heading);
                telemetry.addData("Debug ", lib.i);
                telemetry.addData("RunTime ", chart.runtime);
                telemetry.update();

                idle();
            }
        } catch (Exception e) {}

        //thread1.interrupt();
    }

}
