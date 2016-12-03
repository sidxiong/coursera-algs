package pa2;

import edu.princeton.cs.algs4.StdIn;

/**
 * @author : Sid Xiong
 * @version : 5/28/16.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());

        }

        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }
    }
}
