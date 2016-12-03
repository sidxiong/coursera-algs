package pa3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Sid Xiong
 * @version : 10/30/16.
 */
public class BruteCollinearPoints implements CollinearPoints {
    private LineSegment[] ls;

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 copyOfPoints
        if (points == null) throw new NullPointerException();

        Point[] copyOfPoints = new Point[points.length];
        System.arraycopy(points, 0, copyOfPoints, 0, points.length);

        Arrays.sort(copyOfPoints);
        if (!checked(copyOfPoints)) throw new IllegalArgumentException();
        List<LineSegment> list = new ArrayList<>();
        int len = copyOfPoints.length;

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    for (int l = k + 1; l < len; l++) {
                        if (copyOfPoints[i] == null || copyOfPoints[j] == null ||
                            copyOfPoints[k] == null || copyOfPoints[l] == null) {
                            throw new NullPointerException();
                        } else {
                            double k1 = copyOfPoints[i].slopeTo(copyOfPoints[j]);
                            double k2 = copyOfPoints[i].slopeTo(copyOfPoints[k]);
                            double k3 = copyOfPoints[i].slopeTo(copyOfPoints[l]);
                            if (k1 == k2 && k2 == k3){
                                list.add(new LineSegment(copyOfPoints[i], copyOfPoints[l]));
                            }
                        }
                    }
                }
            }
        }
        ls = list.toArray(new LineSegment[list.size()]);
    }

    private boolean checked(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i-1].compareTo(points[i]) == 0) {
                return false;
            }
        }
        return true;
    }

    public int numberOfSegments() {        // the number of line segments
        return ls.length;
    }

    public LineSegment[] segments() {                // the line segments
        return ls;
    }
}

