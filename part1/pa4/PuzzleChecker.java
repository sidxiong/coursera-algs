package pa4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/******************************************************************************
 * Compilation:  javac PuzzleChecker.java
 * Execution:    java PuzzleChecker filename1.txt filename2.txt ...
 * Dependencies: Board.java Solver.java
 * <p>
 * This program creates an initial board from each filename specified
 * on the command line and finds the minimum number of moves to
 * reach the goal state.
 * <p>
 * % java PuzzleChecker puzzle*.txt
 * puzzle00.txt: 0
 * puzzle01.txt: 1
 * puzzle02.txt: 2
 * puzzle03.txt: 3
 * puzzle04.txt: 4
 * puzzle05.txt: 5
 * puzzle06.txt: 6
 * ...
 * puzzle3x3-impossible: -1
 * ...
 * puzzle42.txt: 42
 * puzzle43.txt: 43
 * puzzle44.txt: 44
 * puzzle45.txt: 45
 ******************************************************************************/

public class PuzzleChecker {

    public static void main(String[] args) {

//        for (String filename : args) {
        for (int step = 31; step <= 40; step++) {
            String filename = String.format("puzzle%02d.txt", step);

            // read in the board specified in the filename
            In in = new In("puzzle/" + filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            Solver solver = new Solver(initial);
            StdOut.println(filename + ": " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
        }
    }
}
