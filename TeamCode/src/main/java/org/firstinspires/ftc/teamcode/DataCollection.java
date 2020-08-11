package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "DataCollection", group = "base")
public class DataCollection extends LinearOpMode {
    TypexChart chart = new TypexChart();


    @Override
    public void runOpMode() throws InterruptedException {

        chart.init(hardwareMap);

        AlgorithmLibrary lib = new AlgorithmLibrary();

        chart.TL.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        chart.TR.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        chart.BL.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        chart.BR.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        waitForStart();
        chart.runtime.reset();


        while (opModeIsActive()) {
            chart.TL.setPower(gamepad1.left_stick_y);
            chart.BL.setPower(gamepad1.left_stick_y);
            chart.TR.setPower(gamepad1.right_stick_y);
            chart.BR.setPower(gamepad1.right_stick_y);

            double range = chart.sensorRange.getDistance(DistanceUnit.INCH);
            telemetry.addData("range", String.format("%.01f in", range));
            telemetry.addData("range", String.format("%.01f avg", lib.avgValue(range)));
            telemetry.log().add("TL Pos: ", chart.TL.getCurrentPosition());
            telemetry.log().add("TR Pos: ", chart.TR.getCurrentPosition());
            telemetry.log().add("BL Pos: ", chart.BL.getCurrentPosition());
            telemetry.log().add("BR Pos: ", chart.BR.getCurrentPosition());

            telemetry.update();
        }
    }

}
