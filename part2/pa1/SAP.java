package part2.pa1;


import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Queue;

import java.util.*;

/**
 * @author : Siyadong Xiong
 * @version : 1/17/17.
 * @email : sx225@cornell.edu
 */
public class SAP {
    private final Digraph G;

    // constructor takes a rooted DAG as argument
    public SAP(Digraph G) {
        if (G == null) throw new NullPointerException();
        this.G = G;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        SAP sca = new SAP(new Digraph(new In("wordnet/digraph3.txt")));

        List<Integer> x       = Arrays.asList(8);
        List<Integer> y       = Arrays.asList(13);
        SCANode       scaNode = sca.bfs(x, y);
        System.out.println(scaNode.length);
        System.out.println(scaNode.ancestor);
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        SCANode scaNode = findSCANode(v, w);
        return scaNode == null ? -1 : scaNode.length;
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        SCANode scaNode = findSCANode(v, w);
        return scaNode == null ? -1 : scaNode.ancestor;
    }

    private SCANode findSCANode(int v, int w) {
        if (v >= G.V() || w >= G.V()) throw new IndexOutOfBoundsException();
        if (v == w) return new SCANode(0, v, 0);

        List<Integer> x = Arrays.asList(v);
        List<Integer> y = Arrays.asList(w);

        return bfs(x, y);
    }

    private void initBFS(Queue<Integer> q, Set<Integer> marked, int[] distTo,
                         Iterable<Integer> nodes) throws IndexOutOfBoundsException {
        for (int node : nodes) {
            if (node >= G.V()) throw new IndexOutOfBoundsException();
            q.enqueue(node);
            distTo[node] = 0;
            marked.add(node);
        }
    }


    private SCANode stepBFS(Queue<Integer> q, Set<Integer> marked, Set<Integer> otherMarked,
                            int[] distTo, int[] distToOfOther) {
        if (!q.isEmpty()) {
            int v = q.dequeue();
            for (int adj : G.adj(v)) {
                if (!marked.contains(adj)) {
                    q.enqueue(adj);
                    marked.add(adj);
                    distTo[adj] = distTo[v] + 1;

                    if (otherMarked.contains(adj)) {
//                        distTo[adj] = distTo[v] + 1;
//                    } else {
                        return new SCANode(distToOfOther[adj] + (distTo[v] + 1), adj, distTo[v] + 1);
                    }
                }
            }
        }
        return null;
    }


    private SCANode bfs(Iterable<Integer> vs, Iterable<Integer> ws) throws IndexOutOfBoundsException {
        int[] distToOfV = new int[G.V()];
        Arrays.fill(distToOfV, Integer.MAX_VALUE);
        int[] distToOfW = new int[G.V()];
        Arrays.fill(distToOfW, Integer.MAX_VALUE);

        Queue<Integer> qv      = new Queue<>();
        Set<Integer>   markedV = new HashSet<>();
        initBFS(qv, markedV, distToOfV, vs);

        Queue<Integer> qw      = new Queue<>();
        Set<Integer>   markedW = new HashSet<>();
        initBFS(qw, markedW, distToOfW, ws);

        for (int v : markedV) {
            if (markedW.contains(v)) return new SCANode(0, v, 0);
        }

        // invariance: markedV and markedW has no intersection
        SCANode candidate = null;

        SCANode node1;
        SCANode node2;
        while (!qv.isEmpty() || !qw.isEmpty()) {
            // each bfs move a step forward
            node1 = stepBFS(qv, markedV, markedW, distToOfV, distToOfW);
            node2 = stepBFS(qw, markedW, markedV, distToOfW, distToOfV);

            // check stop condition
            if (node1 != null) {
                if (candidate == null || node1.length < candidate.length) {
                    candidate = node1;
                }
                if (node1.bfsStep >= candidate.length) {
                    return candidate;
                }
            }
            if (node2 != null) {
                if (candidate == null || node2.length < candidate.length) {
                    candidate = node2;
                }
                if (node2.bfsStep >= candidate.length) {
                    return candidate;
                }
            }
        }
        return candidate;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int length(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null) throw new NullPointerException();
        SCANode scaNode = bfs(subsetA, subsetB);
        return scaNode == null ? -1 : scaNode.length;
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestor(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null) throw new NullPointerException();
        SCANode scaNode = bfs(subsetA, subsetB);
        return scaNode == null ? -1 : scaNode.ancestor;
    }

    private final class SCANode {
        private int bfsStep;
        private int length;
        private int ancestor;

        SCANode(int length, int ancestor, int bfsStep) {
            this.length = length;
            this.ancestor = ancestor;
            this.bfsStep = bfsStep;
        }
    }

}
