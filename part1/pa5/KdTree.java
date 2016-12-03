package pa5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Sid Xiong
 * @version : 11/3/16.
 */
public class KdTree {
    private static final class TreeNode {
        int level;
        Point2D point;
        private RectHV rect;
        TreeNode left, right;

        public TreeNode(int level, Point2D point, RectHV rect) {
            this.level = level;
            this.point = point;
            this.rect = rect;
        }
    }

    private TreeNode root;
    private int size;

    public KdTree() {
        size = 0;
    }

    public boolean isEmpty() {                      // is the set empty?
        return size == 0;
    }

    public int size() {                         // number of points in the set
        return size;
    }

    public void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        root = insert(root, p, 0, new RectHV(0, 0, 1, 1));
    }

    private TreeNode insert(TreeNode curr, Point2D p, int level, RectHV rect) {
        if (curr == null) {
            size++;
            return new TreeNode(level, p, rect);
        } else {
            int side = compare(curr, p);

            RectHV part;
            if (side < 0) {
                if (curr.left == null) {
                    if (curr.level % 2 == 0) { // left
                        part = new RectHV(rect.xmin(), rect.ymin(), curr.point.x(), rect.ymax());
                    } else { // bottom
                        part = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), curr.point.y());
                    }
                } else {
                    part = curr.left.rect;
                }
                curr.left = insert(curr.left, p, level + 1, part);
            } else if (side > 0) {
                if (curr.right == null) {
                    if (curr.level % 2 == 0) {
                        part = new RectHV(curr.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
                    } else {
                        part = new RectHV(rect.xmin(), curr.point.y(), rect.xmax(), rect.ymax());
                    }
                } else {
                    part = curr.right.rect;
                }
                curr.right = insert(curr.right, p, level + 1, part);
            }

            return curr;
        }
    }

    public boolean contains(Point2D p) {            // does the set contain point p?
        return search(root, p) != null;
    }

    private TreeNode search(TreeNode curr, Point2D p) {
        if (curr == null) return null;
        else {
            int comp = compare(curr, p);
            if (comp == 0) {
                return curr;
            } else if (comp < 0) {
                return search(curr.left, p);
            } else {
                return search(curr.right, p);
            }
        }
    }

    private int compare(TreeNode node, Point2D p) {
        if (node.point.equals(p)) return 0;

        double thresh;
        double crit;
        if (node.level % 2 == 0) {
            thresh = node.point.x();
            crit = p.x();
        } else {
            thresh = node.point.y();
            crit = p.y();
        }
        return crit < thresh ? -1 : 1;
    }

    public void draw() {                         // draw all points to standard draw
        draw(root);
    }

    private void draw(TreeNode node) {
        if (node != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.02);
            StdDraw.point(node.point.x(), node.point.y());
            StdDraw.setPenRadius(.004);
            if (node.level % 2 == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.point.x(), node.rect.ymin(),
                             node.point.x(), node.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(node.rect.xmin(), node.point.y(),
                             node.rect.xmax(), node.point.y());
            }
            draw(node.left);
            draw(node.right);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle
        if (rect == null) throw new NullPointerException();

        List<Point2D> list = new ArrayList<>();
        search(list, root, rect);
        return list;
    }

    private void search(List<Point2D> list, TreeNode curr, RectHV rect) {
        if (curr != null && rect.intersects(curr.rect)) {
            if (rect.contains(curr.point)) {
                list.add(curr.point);
            }
            search(list, curr.left, rect);
            search(list, curr.right, rect);
        }
    }

    public Point2D nearest(Point2D p) {             // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new NullPointerException();
        if (this.isEmpty()) return null;
        return searchNearest(root, null, p, Double.POSITIVE_INFINITY);
    }

    public Iterable<Point2D> nearest(Point2D p, int k) {
        throw new UnsupportedOperationException();
    }

    private Point2D searchNearest(TreeNode curr, Point2D candidate, Point2D p, double dis) {
        if (curr == null || curr.rect.distanceSquaredTo(p) > dis) {
            return candidate;
        } else {
            if (curr.point.distanceSquaredTo(p) < dis) {
                candidate = curr.point;
                dis = curr.point.distanceSquaredTo(p);
            }
            int side = compare(curr, p);
            if (side < 0) {
                candidate = searchNearest(curr.left, candidate, p, dis);
                if (candidate.distanceSquaredTo(p) < dis) {
                    dis = candidate.distanceSquaredTo(p);
                }
                candidate = searchNearest(curr.right, candidate, p, dis);
            } else {
                candidate = searchNearest(curr.right, candidate, p, dis);
                if (candidate.distanceSquaredTo(p) < dis) {
                    dis = candidate.distanceSquaredTo(p);
                }
                candidate = searchNearest(curr.left, candidate, p, dis);
            }
            return candidate;
        }
    }

    public Iterable<Point2D> points() {    // Required API in COS226's assignment rather than in the Coursera one.
        List<Point2D> list = new ArrayList<>(size);

        if (root != null) {
            Queue<TreeNode> q = new Queue<>();
            q.enqueue(root);
            while (!q.isEmpty()) {
                TreeNode x = q.dequeue();
                list.add(x.point);
                if (x.left != null) q.enqueue(x.left);
                if (x.right != null) q.enqueue(x.right);
            }
        }

        return list;
    }


    public static void main(String[] args) {                 // unit testing of the methods (optional)
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(.7, .2));
        kdTree.insert(new Point2D(.5, .4));
        kdTree.insert(new Point2D(.2, .3));
        kdTree.insert(new Point2D(.4, .7));
        kdTree.insert(new Point2D(.4, .7));
        System.out.println(kdTree.size());
        System.out.println(kdTree.contains(new Point2D(.6, .5)));
        System.out.println(kdTree.contains(new Point2D(.6, .3)));
        System.out.println(kdTree.contains(new Point2D(.4, .7)));
        kdTree.draw();
        System.out.println("---");
        for (Point2D p : kdTree.points()) {
            System.out.println(p);
        }
    }
}
