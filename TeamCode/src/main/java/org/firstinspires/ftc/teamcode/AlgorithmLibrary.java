package org.firstinspires.ftc.teamcode;


import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.Locale;

public class AlgorithmLibrary {
    TypexChart chart = new TypexChart();

    //Average Value
    double values[] = new double[10];
    int indexValue = 0;
    double average;

    public double avgValue(double value) {
        double total = 0;
        for (int k = 0; k < values.length; k++) {
            total += values[k];
        }
        average = total / 10;
        values[indexValue] = value;
        indexValue++;
        if (indexValue > 9) indexValue = 0;

        return average;
    }

    //IMU Values
    public Orientation angles;
    public Acceleration gravity;
    public double heading;
    public double roll;
    public double pitch;
    public double accX;
    public double accY;
    public double accZ;

    public int i = 0;
}

