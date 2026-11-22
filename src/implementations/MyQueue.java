package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

/**
 * A doubly linked list-backed implementation of the {@link QueueADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class delegates all storage to an internal {@link MyDLL}, treating
 * index {@code 0} as the front of the queue and the last index as the back.
 * {@link #enqueue(String)} appends to the back; {@link #dequeue()} and
 * {@link #peek()} access the front. A queue follows a
 * <strong>First-In, First-Out (FIFO)</strong> ordering policy: the first
 * element enqueued is the first one returned.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyQueue<E>}), allowing any object type to be stored in the queue.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyQueue implements QueueADT {

    /** List that stores the queue items. */
    private MyDLL list;

    /**
     * Makes an empty queue.
     */
    public MyQueue() {
        list = new MyDLL();
    }

    /**
     * Adds an item to the back.
     *
     * @param toAdd item to add
     * @throws NullPointerException if toAdd is null
     */
    @Override
    public void enqueue(String toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot enqueue null");
        }
        list.add(toAdd);
    }

    /**
     * Removes and returns the front item.
     *
     * @return the front item
     * @throws EmptyQueueException if the queue is empty
     */
    @Override
    public String dequeue() throws EmptyQueueException {
        if (list.isEmpty()) {
            throw new EmptyQueueException("Queue is empty");
        }
        return list.remove(0);
    }

    /**
     * Returns the front item without removing it.
     *
     * @return the front item
     * @throws EmptyQueueException if the queue is empty
     */
    @Override
    public String peek() throws EmptyQueueException {
        if (list.isEmpty()) {
            throw new EmptyQueueException("Queue is empty");
        }
        return list.get(0);
    }

    /**
     * Removes all items.
     */
    @Override
    public void dequeueAll() {
        list.clear();
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if empty
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Checks if an item is in the queue.
     *
     * @param toFind item to look for
     * @return true if found
     * @throws NullPointerException if toFind is null
     */
    @Override
    public boolean contains(String toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null");
        }
        return list.contains(toFind);
    }

    /**
     * Finds an item's position from the front (front is 1).
     *
     * @param toFind item to look for
     * @return position of the item, or -1 if not found
     */
    @Override
    public int search(String toFind) {
        if (toFind == null) {
            return -1;
        }

        int pos = 1;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (toFind.equals(it.next())) {
                return pos;
            }
            pos++;
        }
        return -1;
    }

    /**
     * Returns an iterator from front to back.
     *
     * @return iterator over the queue
     */
    @Override
    public Iterator iterator() {
        return list.iterator();
    }

    /**
     * Checks if two queues have the same items in the same order.
     *
     * @param that queue to compare with
     * @return true if equal
     * @throws NullPointerException if that is null
     */
    @Override
    public boolean equals(QueueADT that) {
        if (that == null) {
            throw new NullPointerException("Cannot compare to null queue");
        }
        if (this == that) {
            return true;
        }
        if (this.size() != that.size()) {
            return false;
        }

        Iterator mine = this.iterator();
        Iterator theirs = that.iterator();
        while (mine.hasNext() && theirs.hasNext()) {
            if (!mine.next().equals(theirs.next())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Puts the queue items into an array, front to back.
     *
     * @param holder array to fill
     * @return array of the items
     * @throws NullPointerException if holder is null
     */
    @Override
    public String[] toArray(String[] holder) throws NullPointerException {
        if (holder == null) {
            throw new NullPointerException("Holder array cannot be null");
        }
        return list.toArray(holder);
    }

    /**
     * Returns the number of items.
     *
     * @return the size
     */
    @Override
    public int size() {
        return list.size();
    }
}
