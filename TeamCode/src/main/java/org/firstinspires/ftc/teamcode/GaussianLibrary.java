package org.firstinspires.ftc.teamcode;

public class GaussianLibrary {
    GaussianLibrary lib = new GaussianLibrary();
    public double[] gaussian(double mean, double variance) {
        double gaussian[] = new double[2];
        gaussian[0] = mean;
        gaussian[1] = variance;

        return gaussian;
    }

    public double mean(double[] gaussian) {
        return gaussian[0];
    }

    public double variance(double[] gaussian) {
        return gaussian[1];
    }

    public double[] predict(double[] pos, double[] movement) {
        return gaussian(lib.mean(pos) + lib.mean(movement), lib.variance(pos) + lib.variance(movement));
    }

    public double[] gaussian_multiply(double[] g1, double[] g2) {
        double mean = (lib.variance(g1)*lib.mean(g2) + lib.variance(g2)* lib.mean(g1)) / (lib.variance(g1) + lib.variance(g2));
        double variance = (lib.variance(g1) * lib.variance(g2)) / (lib.variance(g1) + lib.variance(g2));
        return lib.gaussian(mean, variance);
    }

    public double[] update(double[] prior, double[] likelihood) {
        double[] posterior = lib.gaussian_multiply(likelihood, prior);
        return posterior;
    }
}
