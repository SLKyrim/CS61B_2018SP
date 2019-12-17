package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid; // the grid of sites
    private int size; // the size of grid
    private int numOpenSites = 0; // cache the number of open sites
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufNoVirtualBottom; // avoid backwash from bottom

    // virtual top site to which the site connected should be full
    private int top = 0;
    // virtual bottom site to which the site connected can reach the bottom of grid
    private int bottom;
    // access the sites around this site
    private int[][] arounds = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /** create N-by-N grid, with all sites initially blocked */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("The size of grid N must be larger than 0");
        }
        size = N;
        grid = new boolean[N][N]; // default value of boolean is false, aka blocked site
        bottom = N * N + 1;

        // N-by-N sites plus virtual top and bottom site
        uf = new WeightedQuickUnionUF(N * N + 2);
        // N-by-N sites plus virtual top site
        ufNoVirtualBottom = new WeightedQuickUnionUF(N * N + 1);
    }

    /** Transfer 2D index to 1D */
    private int xyTo1D(int row, int col) {
        // virtual top site occupy the index 0, necessary to plus one
        return row * size + col + 1;
    }

    /** open the site (row, col) if it is not open already */
    public void open(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("Cannot open the site out of bounds");
        }
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            numOpenSites += 1;
        }
        if (row == 0) {
            uf.union(xyTo1D(row, col), top);
            ufNoVirtualBottom.union(xyTo1D(row, col), top);
        }
        if (row == size - 1) {
            uf.union(xyTo1D(row, col), bottom);
        }
        for (int[] around : arounds) {
            int aRow = row + around[0];
            int aCol = col + around[1];
            if (aRow >= 0 && aRow < size) {
                if (aCol >= 0 && aCol < size) {
                    if (isOpen(aRow, aCol)) {
                        uf.union(xyTo1D(aRow, aCol), xyTo1D(row, col));
                        ufNoVirtualBottom.union(xyTo1D(aRow, aCol), xyTo1D(row, col));
                    }
                }
            }
        }
    }

    /** is the site (row, col) open? */
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("Cannot check the site out of bounds isOpen");
        }
        return grid[row][col];
    }

    /** is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("Cannot check the site out of bounds isFull");
        }
        // return uf.connected(xyTo1D(row, col), top); // lead to backwash from bottom
        return ufNoVirtualBottom.connected(xyTo1D(row, col), top);

    }

    /** number of open sites */
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    /** does the system percolate? */
    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    /** use for unit testing (not required) */
    public static void main(String[] args) {
        
    }
}
