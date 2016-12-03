package pa1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * @author : Sid Xiong
 * @version : 10/29/16.
 */
public class PercolationStats {
    private double mean;
    private double stddev;
    private double confLo;
    private double confHi;

    public PercolationStats(int n, int trials) {   // perform trials independent experiments on an n-by-n grid
        double[] ratio = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            int count = 0;
            while (!perc.percolates()) {
                int idx = StdRandom.uniform(n * n);
                int row = idx / n + 1;
                int col = idx % n + 1;
                while (perc.isOpen(row, col)) {
                    idx = StdRandom.uniform(n * n);
                    row = idx / n + 1;
                    col = idx % n + 1;
                }

                perc.open(row, col);
                count++;
            }
            ratio[i] = (double) count / (n * n);
        }
        this.mean = StdStats.mean(ratio);
        this.stddev = StdStats.stddev(ratio);
        this.confLo = this.mean - 1.96 * this.stddev / Math.sqrt(trials);
        this.confHi = this.mean + 1.96 * this.stddev / Math.sqrt(trials);
    }

    public double mean() {                         // sample mean of percolation threshold
        return this.mean;
    }

    public double stddev() {                        // sample standard deviation of percolation threshold
        return this.stddev;
    }

    public double confidenceLo() {                  // low  endpoint of 95% confidence interval
        return this.confLo;
    }

    public double confidenceHi() {                  // high endpoint of 95% confidence interval
        return this.confHi;
    }


    @Override
    public String toString() {
        return "mean\t= " + mean + "\nstddev\t= " +
            stddev + "\n95% conf interval\t= " +
            confLo+ ", " + confHi;
    }

    public static void main(String[] args) {    // test client (described below)
        System.out.println(new PercolationStats(200, 100));
        System.out.println(new PercolationStats(200, 100));
        System.out.println(new PercolationStats(2, 10000));
        System.out.println(new PercolationStats(2, 10000));
    }
}
