package org.firstinspires.ftc.teamcode;

public class AlgorithmLibrary {

    double values[] = new double[10];
    int indexValue = 0;
    double average;

    public double avgValue(double value) {
        double total = 0;
        for (int k = 0; k < values.length; k++) {
            total += values[k];
        }
        average = total/10;
        values[indexValue] = value;
        indexValue++;
        if (indexValue > 9) indexValue = 0;

        return  average;
    }
}
