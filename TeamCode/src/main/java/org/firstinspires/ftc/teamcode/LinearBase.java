package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class LinearBase extends LinearOpMode {

    private DcMotor.RunMode defaultRunMode = DcMotor.RunMode.RUN_USING_ENCODER;

    boolean verbose = true; //Extra Logging

    public boolean running    = false; // for if the shoot thread is running
    public boolean stopThread = false; // for use when the button is broken. Turn to true


    //Initialization of things
    public void initialise() {
        double start = getRuntime();

        telemetry.addData("Done: ", "Starting init");
        telemetry.update();

        TypexChart chart = new TypexChart();

        if(verbose){telemetry.addData("Done: ", "Took %.2f seconds", (getRuntime()-start));}
        telemetry.update();

    }

    public void setDriveMotorSpeed(double leftSpeed, double rightSpeed) {
        
    }


}
