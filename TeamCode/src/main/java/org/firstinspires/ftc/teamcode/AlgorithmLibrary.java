package org.firstinspires.ftc.teamcode;

import java.io.IOException;

public class AlgorithmLibrary {

    public AlgorithmLibrary() throws IOException {
        Logging.setup();
        Logging.log("Starting Data Collection Logging for AlgLib.");
    }

    public TypexChart chart = new TypexChart();

    double values[] = new double[10];
    int indexValue = 0;
    double average;

    public boolean imuDataThreadRunning = false;
    public boolean imuDataStopThread = false;
    public boolean encDataThreadRunning = false;
    public boolean encDataStopThread = false;
    public boolean rangeDataThreadRunning = false;
    public boolean rangeDataStopThread = false;

    public void imuDataRecordThread() {
        if (!imuDataThreadRunning) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    imuDataThreadRunning = true;


                    while (!imuDataStopThread) {}

                    Logging.log("imu Orientation: "+ chart.imu.getAngularOrientation());
                    Logging.log("imu Acceleration: "+ chart.imu.getAcceleration());
                    imuDataThreadRunning = false;
                    imuDataStopThread = false;
                }
            }).start();
        }
    }

    public void GaussianVariable(double mean, double variance) {

    }

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
