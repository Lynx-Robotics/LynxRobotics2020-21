package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

import java.util.Locale;

public class IMUDataThread extends Thread {

    TypexChart chart = new TypexChart();
    AlgorithmLibrary lib = new AlgorithmLibrary();

    public void run() {
        try {
            lib.angles = chart.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            lib.gravity = chart.imu.getGravity();
            lib.i++;

            lib.heading = formatAngle(lib.angles.angleUnit, lib.angles.firstAngle);
            lib.roll = formatAngle(lib.angles.angleUnit, lib.angles.secondAngle);
            lib.pitch = formatAngle(lib.angles.angleUnit, lib.angles.thirdAngle);
            lib.accX = lib.gravity.xAccel;
            lib.accY = lib.gravity.yAccel;
            lib.accZ = lib.gravity.zAccel;

        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    Double formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    Double formatDegrees(double degrees) {
        return Double.valueOf(String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees)));
    }
}
