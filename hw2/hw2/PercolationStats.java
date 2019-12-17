package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int t; // the number of experiments
    private double[] xt; // cache the fraction of open sites in experiments

    /** perform T independent experiments on an N-by-N grid */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        t = T;
        xt = new double[t];
        for (int i = 0; i < t; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            xt[i] = (double) p.numberOfOpenSites() / (N * N);
        }
    }

    /** sample mean of percolation threshold */
    public double mean() {
        return StdStats.mean(xt);
    }

    /** sample standard deviation of percolation threshold */
    public double stddev() {
        return StdStats.stddev(xt);
    }

    /** low endpoint of 95% confidence interval */
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(t);
    }

    /** high endpoint of 95% confidence interval */
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(t);
    }
}
