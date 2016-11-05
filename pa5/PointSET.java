package pa5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author : Sid Xiong
 * @version : 11/3/16.
 */

public class PointSET {
    private Set<Point2D> set;

    public PointSET() {                              // construct an empty set of points
        set = new TreeSet<>();
    }

    public boolean isEmpty() {                      // is the set empty?
        return set.isEmpty();
    }

    public int size() {                         // number of points in the set
        return set.size();
    }

    public void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null) throw new NullPointerException();
        if (!set.contains(p)) {
            set.add(p);
        }
    }

    public boolean contains(Point2D p) {            // does the set contain point p?
        if (p == null) throw new NullPointerException();
        return set.contains(p);
    }

    public void draw() {                         // draw all points to standard draw
        for (Point2D p : set) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle
        if (rect == null) throw new NullPointerException();
        List<Point2D> list = new ArrayList<>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                list.add(p);
            }
        }
        return list;
    }

    public Point2D nearest(Point2D p) {             // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new NullPointerException();
        double dis = Double.POSITIVE_INFINITY;
        Point2D nearest = null;
        for (Point2D pt : set) {
            if (pt.distanceSquaredTo(p) < dis) {
                nearest = pt;
                dis = pt.distanceSquaredTo(p);
            }
        }
        return nearest;
    }

    public Iterable<Point2D> points() {    // Required API in COS226's assignment rather than in the Coursera one.
        return set;
    }

    public static void main(String[] args) {                 // unit testing of the methods (optional)
    }
}
