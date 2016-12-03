package pa5;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

/******************************************************************************
 *  Compilation:  javac NearestNeighborVisualizer.java
 *  Execution:    java NearestNeighborVisualizer input.txt
 *  Dependencies: PointSET.java KdTree.java
 *
 *  Read points from a file (specified as a command-line argument) and
 *  draw to standard draw. Highlight the closest point to the mouse.
 *
 *  The nearest neighbor according to the brute-force algorithm is drawn
 *  in red; the nearest neighbor using the kd-tree algorithm is drawn in blue.
 *
 ******************************************************************************/

public class NearestNeighborVisualizer {


    public static void main(String[] args) {
//        String filename = args[0];
        String filename = "kdtree/circle10.txt";
        In in = new In(filename);

//        StdDraw.enableDoubleBuffering();

        // initialize the two data structures with point from standard input
//        PointSET set = new PointSET();
        KdTree set = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            set.insert(p);
//            brute.insert(p);
        }

        // draw all of the points

        StdDraw.clear();
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLACK);
        set.draw();
        while (true) {
            if (StdDraw.mousePressed()) {
                StdDraw.clear();
                StdDraw.setPenRadius(0.02);
                StdDraw.setPenColor(StdDraw.BLACK);
                set.draw();
                // the location (x, y) of the mouse
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();

                System.out.println("[query] x: " + x + "; y: " + y);

                Point2D query = new Point2D(x, y);

                StdDraw.setPenRadius(0.02);
                StdDraw.setPenColor(StdDraw.GREEN);
                query.draw();

//            brute.draw();

                // draw in red the nearest neighbor (using brute-force algorithm)
//            StdDraw.setPenRadius(0.03);
//            StdDraw.setPenColor(StdDraw.RED);
//            brute.nearest(query).draw();

                // draw in blue the nearest neighbor (using kd-tree algorithm)
                StdDraw.setPenColor(StdDraw.MAGENTA);
                Point2D nearest = set.nearest(query);
                System.out.println("[nearest] x: " + nearest.x() + "; y: " + nearest.y());
                nearest.draw();
                StdDraw.show(50);

            }
        }
    }

}
