package org.firstinspires.ftc.teamcode;

import java.util.Arrays;

/* Neverest 40 Motors have 1120 ticks per revolution
* HD Mecanum wheels have a diameter of 4in
* Circumference of wheels = 12.566370614359172953850573533118‬in
* Ex. 5600ticks/1120 = 5 * C = 62in
*
* */

public class GaussianLibrary {
    public double[] gaussian(double mean, double variance) {
        double gaussian[] = new double[] {mean, variance};

        return gaussian;
    }

    public double mean(double[] gaussian) {
        return gaussian[0];
    }

    public double variance(double[] gaussian) {
        return gaussian[1];
    }

    public double[] predict(double[] pos, double[] movement) {
        System.out.println();
        return gaussian((mean(pos) + mean(movement)), (variance(pos) + variance(movement)));
    }

    public double[] gaussian_multiply(double[] g1, double[] g2) {
        double mean = (variance(g1)*mean(g2) + variance(g2)* mean(g1)) / (variance(g1) + variance(g2));
        double variance = (variance(g1) * variance(g2)) / (variance(g1) + variance(g2));
        return gaussian(mean, variance);
    }

    public double[] update(double[] prior, double[] likelihood) {
        double[] posterior = gaussian_multiply(likelihood, prior);
        return posterior;
    }


    public static void main(String[] args) {
        GaussianLibrary lib = new GaussianLibrary();

        double process_var = 1; //how much error in the process model
        double sensor_var = 2;  //the amount of variance in each sensor measurement

        double[] x = lib.gaussian(0, Math.pow(20, 2)); //Starting pos in gaussian
        int velocity = 1;
        double dt = 1;
        double[] process_model = lib.gaussian(velocity*dt, process_var); //How I think the robot is moving

        double[] zs = {1.3535959735108178, 1.8820653967131618, 4.341047429453569, 7.156332673205118, 6.938695089418526, 6.843912342028484, 9.846824080052299, 12.553482049375292, 16.2730841073834, 14.800411177015299};

        for (int i = 1; i < zs.length; i++) {
            double[] prior = lib.predict(x, process_model);
            double[] likelihood = lib.gaussian(zs[i], sensor_var);
            x = lib.update(prior, likelihood);
            System.out.println("Predict: " + Arrays.toString(prior));
            System.out.println("Update:  " + Arrays.toString(x));
        }
    }
}
