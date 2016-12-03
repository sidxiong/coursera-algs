package pa1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author : Sid Xiong
 * @version : 10/29/16.
 */
public class Percolation {
    private int N;
    private WeightedQuickUnionUF uf;
    private int[][] status;
    private boolean percolates;

    private static int[][] surr = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    private static int BLOCK = 1;
    private static int OPEN = 1 << 1;
    private static int CONNECT_WITH_TOP = OPEN << 1;
    private static int CONNECT_WITH_BTM = CONNECT_WITH_TOP << 1;


    public Percolation(int n) {                // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException();
        this.N = n;

        this.status = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.status[i][j] = BLOCK;

                if (i == 0) {
                    this.status[i][j] |= CONNECT_WITH_TOP;
                }

                if (i == n - 1) {
                    this.status[i][j] |= CONNECT_WITH_BTM;
                }
            }
        }

        this.uf = new WeightedQuickUnionUF(n * n);
        this.percolates = false;
    }

    public void open(int row, int col) {       // open site (row, col) if it is not open already
        if (!valid(row, col)) throw new IllegalArgumentException();

        if ((status[row - 1][col - 1] & OPEN) == 0) {
            int x = (status[row - 1][col - 1] |= OPEN);
            for (int[] d : surr) {
                int rr = row + d[0];
                int cc = col + d[1];
                if (1 <= rr && rr <= N &&
                    1 <= cc && cc <= N &&
                    isOpen(rr, cc)) {

                    int root = uf.find(getUFIdx(rr, cc));
                    x = status[root / N][root % N] | x;

                    uf.union(getUFIdx(rr, cc), getUFIdx(row, col));
                }
            }

            int newRoot = uf.find(getUFIdx(row, col));
            status[newRoot / N][newRoot % N] |= x;

            if ((status[newRoot / N][newRoot % N] & CONNECT_WITH_TOP) > 0 &&
                (status[newRoot / N][newRoot % N] & CONNECT_WITH_BTM) > 0) {
                this.percolates = true;
            }
        }
    }

    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        if (!valid(row, col)) throw new IllegalArgumentException();
        return (status[row - 1][col - 1] & OPEN) > 0;
    }

    public boolean isFull(int row, int col) {  // is site (row, col) full?
        if (!valid(row, col)) throw new IllegalArgumentException();
        int root = uf.find(getUFIdx(row, col));
        return (status[root / N][root % N] & OPEN) > 0 &&
               (status[root / N][root % N] & CONNECT_WITH_TOP) > 0;
    }

    public boolean percolates() {              // does the system percolate?
        return this.percolates;
    }


    private int getUFIdx(int row, int col) {
        return (row - 1) * N + col - 1;
    }

    private boolean valid(int row, int col) {
        return 0 < row && row <= N &&
               0 < col && col <= N;
    }

    public static void main(String[] args) {  // test client (optional)
    }
}
