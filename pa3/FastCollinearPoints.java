package pa3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author : Sid Xiong
 * @version : 10/30/16.
 */
public class FastCollinearPoints implements CollinearPoints {
    private LineSegment[] ls;

    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more copyOfPoints
        if (points == null) throw new NullPointerException();

        Point[] copyOfPoints = new Point[points.length];
        System.arraycopy(points, 0, copyOfPoints, 0, points.length);

        List<LineSegment> list = new ArrayList<>();

        Arrays.sort(copyOfPoints);
        if (!checked(copyOfPoints)) throw new IllegalArgumentException();

        Point[] copy = new Point[copyOfPoints.length];
        System.arraycopy(copyOfPoints, 0, copy, 0, copyOfPoints.length);

        for (Point pt : copyOfPoints) {
            Comparator<Point> comp = pt.slopeOrder();
            Arrays.sort(copy, comp);
            int i = 0, j = 0;
            while (j < copy.length) {
                if (comp.compare(copy[i], copy[j]) == 0) {
                    j++;
                } else {
                    if (j - i >= 3) {
                        addLS(list, copy, i, j);
                    }
                    i = j++;
                }
            }
            if (j - i >= 3) {
                addLS(list, copy, i, j);
            }
        }

        ls = list.toArray(new LineSegment[0]);
    }

    private void addLS(List<LineSegment> list, Point[] copy, int i, int j) {
        Point[] segPoints = new Point[j-i+1];
        segPoints[0] = copy[0];
        Point ori = copy[0];
        System.arraycopy(copy, i, segPoints, 1, j - i);
        Arrays.sort(segPoints);

        // check that this LineSegment appears for the first time
        if (segPoints[0] == ori) {
            list.add(new LineSegment(segPoints[0], segPoints[segPoints.length - 1]));
        }
    }

    private boolean checked(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i-1].compareTo(points[i]) == 0) {
                return false;
            }
        }
        return true;
    }

    public int numberOfSegments() {       // the number of line segments
        return ls.length;
    }

    public LineSegment[] segments() {                // the line segments
        return ls;
    }
}
