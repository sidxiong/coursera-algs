package pa2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author : Sid Xiong
 * @version : 5/28/16.
 */

public class Deque<Item> implements Iterable<Item> {

    private static final class Node<Item> {
        Item value;
        Node<Item> prev;
        Node<Item> next;

        Node(Item value, Node<Item> prev, Node<Item> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        Node(Item value) {
            this(value, null, null);
        }
    }


    private Node<Item> dummy;
    private int size;

    public Deque() {                           // construct an empty deque
        dummy = new Node<>(null);
        dummy.next = dummy;
        dummy.prev = dummy;
        size = 0;
    }

    public boolean isEmpty() {                // is the deque empty?
        return size == 0;
    }

    public int size() {                       // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) {          // add the item to the front
        if (item == null) {
            throw new NullPointerException();
        } else {
            Node<Item> node = new Node<>(item, dummy, dummy.next);
            dummy.next.prev = node;
            dummy.next = node;
            size++;
        }
    }

    public void addLast(Item item) {          // add the item to the end
        if (item == null) {
            throw new NullPointerException();
        } else {
            Node<Item> node = new Node<>(item, dummy.prev, dummy);
            dummy.prev.next = node;
            dummy.prev = node;
            size++;
        }
    }

    public Item removeFirst() {               // remove and return the item from the front
        if (dummy.next == dummy) {
            throw new NoSuchElementException();
        } else {
            Node<Item> first = dummy.next;
            dummy.next = first.next;
            first.next.prev = dummy;
            size--;
            return first.value;
        }
    }

    public Item removeLast() {                // remove and return the item from the end
        if (dummy.prev == dummy) {
            throw new NoSuchElementException();
        } else {
            Node<Item> last = dummy.prev;
            dummy.prev = last.prev;
            last.prev.next = dummy;
            size--;
            return last.value;
        }
    }

    public Iterator<Item> iterator() {        // return an iterator over items in order from front to end
        return new Iterator<Item>() {
            Node<Item> ptr;
            {
                ptr = dummy;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasNext() {
                return ptr.next != dummy;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Node<Item> next = ptr.next;
                ptr = ptr.next;
                return next.value;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<Item> iterator = this.iterator(); iterator.hasNext(); ) {
            Item v = iterator.next();
            sb.append(v);
            if (iterator.hasNext()) sb.append(" -> ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        System.out.println(deque);
        deque.addFirst("ahha");
        deque.addFirst("b");
        deque.addFirst("c");
        deque.addLast("z");
        deque.addLast("y");
        System.out.println(deque);
        deque.removeFirst();
        deque.removeFirst();
        deque.removeLast();
        System.out.println(deque);
        deque.removeFirst();
        deque.removeLast();
        System.out.println(deque);
    }
}
