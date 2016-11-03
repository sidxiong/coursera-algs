package pa4;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Sid Xiong
 * @version : 10/30/16.
 */
public class Board {
    private int blankIdx;
    private int[] data;
    private int N;

    public Board(int[][] blocks) {          // construct a board from an n-by-n array of data
        if (blocks == null || (N = blocks.length) == 0 || blocks[0].length == 0)
            throw new IllegalArgumentException();

        this.data = new int[N * N];
        int i = 0;
        for (int[] r : blocks) {
            for (int elem : r) {
                this.data[i] = elem;
                if (elem == 0) {
                    blankIdx = i;
                }
                i++;
            }
        }

    }

    private Board(int[] data, int N) {
        this.data = data;
        this.N = N;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0) {
                blankIdx = i;
                break;
            }
        }
    }

    public int dimension() {                // board dimension n
        return N;
    }

    public int hamming() {                   // number of data out of place
        int dis = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0 && data[i] != i + 1) {
                dis++;
            }
        }
        return dis;
    }

    public int manhattan() {                 // sum of Manhattan distances between data and goal
        int dis = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0 && data[i] != i + 1) {
                int curr_row = i / N;
                int curr_col = i % N;
                int should_row = (data[i] - 1) / N;
                int should_col = (data[i] - 1) % N;
                dis += Math.abs(should_row - curr_row) + Math.abs(should_col - curr_col);
            }
        }
        return dis;
    }

    public boolean isGoal() {                // is this board the goal board?
        if (data[data.length - 1] != 0) return false;
        else {
            for (int i = 0; i < data.length - 1; i++) {
                if (data[i] != i + 1) {
                    return false;
                }
            }
            return true;
        }

    }

    public Board twin() {                   // a board that is obtained by exchanging any pair of data
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N - 1; c++) {
                if (data[r * N + c] != 0 && data[r * N + c + 1] != 0) {
                    int oriIdx = r * N + c;
                    int dstIdx = r * N + c + 1;
                    return getBoardByExch(oriIdx, dstIdx);
                }
            }
        }
        //won't here
        return this;
    }

    private Board getBoardByExch(int oriIdx, int dstIdx) {
        int[] newData = new int[data.length];
        System.arraycopy(data, 0, newData, 0, data.length);
        int t = newData[oriIdx];
        newData[oriIdx] = newData[dstIdx];
        newData[dstIdx] = t;
        return new Board(newData, N);
    }

    public boolean equals(Object y) {       // does this board equal y?
        if (y == this) return true;

        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }


        Board yy = (Board) y;
        if (data.length != yy.data.length) return false;
        for (int i = 0; i < data.length; i++) {
            try {
                if (data[i] != yy.data[i]) {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {     // all neighboring boards
        List<Board> neighbors = new ArrayList<>(4);
        int blankRow = blankIdx / N;
        int blandCol = blankIdx % N;
        for (int[] d : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
            int row = blankRow + d[0];
            int col = blandCol + d[1];
            if (0 <= row && row < N &&
                0 <= col && col < N) {
                neighbors.add(getBoardByExch(blankIdx, row * N + col));
            }
        }
        return neighbors;
    }

    public String toString() {               // string representation of this board (in the output format specified below)
        StringBuilder sb = new StringBuilder();
        sb.append(N).append("\n");
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                sb.append(String.format("%2d ", data[r * N + c]));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) { // unit tests (not graded)
        int[][] b = new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board init = new Board(b);
        System.out.println(init);
        System.out.println(init.manhattan());
        System.out.println(init.hamming());
    }
}
