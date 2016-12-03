package pa4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

/**
 * @author : Sid Xiong
 * @version : 11/2/16.
 */
public class Solver {
    private static final class SearchNode {
        private Board board;
        private int moves;
        private SearchNode prev;
        private int hamming;
        private int manhattan;

        private SearchNode(Board board, int moves, SearchNode prev) {
            this(board, moves, prev, -1);
        }

        private SearchNode(Board board, int moves, SearchNode prev, int manhattan) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.hamming = -1;
            this.manhattan = manhattan;
        }

        private int getHammingPriority() {
            if (this.hamming == -1) {
                this.hamming = board.hamming();
            }
            return this.hamming + moves;
        }

        private int getManhattanPriority() {
            if (this.manhattan == -1) {
                this.manhattan = board.manhattan();
            }
            return this.manhattan + moves;
        }

        private Iterable<Board> pathToThis() {
            Stack<Board> path = new Stack<>();
            SearchNode ptr = this;
            while (ptr != null) {
                path.push(ptr.board);
                ptr = ptr.prev;
            }
            return path;
        }
    }

    private int moves;

    private static final Comparator<SearchNode> HAMMINGCOMP = new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            return o1.getHammingPriority() - o2.getHammingPriority();
        }
    };

    private static final Comparator<SearchNode> MANHATTANCOMP = new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            return o1.getManhattanPriority() - o2.getManhattanPriority();
        }
    };

    private static final Comparator<SearchNode> COMP = new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int m1 = o1.getManhattanPriority();
            int m2 = o2.getManhattanPriority();
            if (m1 == m2) {
                return o1.getHammingPriority() - o2.getHammingPriority();
            } else {
                return m1 - m2;
            }
        }
    };

    private Iterable<Board> path;

    public Solver(Board initial) {           // find a solution to the initial board (using the A* algorithm)
        this(initial, COMP);
    }

    private Solver(Board initial, Comparator<SearchNode> comp) {
        if (initial == null) throw new NullPointerException();

        MinPQ<SearchNode> pq = new MinPQ<>(comp);
        MinPQ<SearchNode> alterPQ = new MinPQ<>(comp);
        pq.insert(new SearchNode(initial, 0, null));
        alterPQ.insert(new SearchNode(initial.twin(), 0, null));

        while (!pq.isEmpty() && !alterPQ.isEmpty()) {
            SearchNode currNode = pq.delMin();
            SearchNode alterCurrNode = alterPQ.delMin();

            if (currNode.board.isGoal()) {
                path = currNode.pathToThis();
                moves = currNode.moves;
                break;
            } else if (alterCurrNode.board.isGoal()) {
                path = null;
                moves = -1;
                break;
            } else {
                for (Board b : currNode.board.neighbors()) {
                    if (currNode.prev == null ||
                        !b.equals(currNode.prev.board)) {
                        pq.insert(new SearchNode(b, currNode.moves + 1, currNode));
                    }
                }
                for (Board b : alterCurrNode.board.neighbors()) {
                    if (alterCurrNode.prev == null ||
                        !b.equals(alterCurrNode.prev.board)) {
                        alterPQ.insert(new SearchNode(b, alterCurrNode.moves + 1, alterCurrNode));
                    }
                }
            }
        }
    }

    public boolean isSolvable() {            // is the initial board solvable?
        return moves != -1;
    }

    public int moves() {                     // min number of moves to solve initial board; -1 if unsolvable
        return moves;
    }

    public Iterable<Board> solution() {      // sequence of boards in a shortest solution; null if unsolvable
        return path;
    }

    public static void main(String[] args) { // solve a slider puzzle (given below)

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
