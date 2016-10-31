package pa2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author : Sid Xiong
 * @version : 5/28/16.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int size;
    private static final int INIT_CAPACITY = 50;

    public RandomizedQueue() {                // construct an empty randomized queue
        this.q = (Item[]) new Object[INIT_CAPACITY];
        this.size = 0;
    }

    public boolean isEmpty() {                 // is the queue empty?
        return size == 0;
    }

    public int size() {                        // return the number of items on the queue
        return size;
    }

    public void enqueue(Item item) {          // add the item
        if (item == null) throw new NullPointerException();
        q[size++] = item;
        if (size == q.length) {
            resize(size * 2);
        }
    }

    public Item dequeue() {                   // remove and return a random item
        if (size == 0) throw new NoSuchElementException();
        int idx = StdRandom.uniform(size);
        Item t = q[idx];
        q[idx] = q[size - 1];
        q[size - 1] = t;
        if (size - 1 == q.length / 4) {
            resize(q.length / 2);
        }
        return q[size-- - 1];
    }

    public Item sample() {                    // return (but do not remove) a random item
        if (size == 0) throw new NoSuchElementException();
        int idx = StdRandom.uniform(size);
        return q[idx];
    }

    private void resize(int newSize) {
        Item[] copy = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            copy[i] = this.q[i];
        }
        this.q = copy;
    }

    @Override
    public Iterator<Item> iterator() {         // return an independent iterator over items in random order
        return new Iterator<Item>() {
            int[] idx;
            int curr;

            {
                idx = new int[size];
                curr = 0;
                for (int i = 0; i < idx.length; i++) {
                    idx[i] = i;
                }
                StdRandom.shuffle(idx);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasNext() {
                return curr < size;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                return q[idx[curr++]];
            }
        };
    }

    public static void main(String[] args) {   // unit testing
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(10);
        System.out.println("size: " + rq.size());
        System.out.println("item: " + rq.dequeue());

        for (Integer x : rq) {
            System.out.println(x);
        }
    }
}
