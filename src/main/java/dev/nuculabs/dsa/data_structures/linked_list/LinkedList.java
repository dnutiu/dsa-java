package dev.nuculabs.dsa.data_structures.linked_list;


import java.util.*;
import java.util.function.Consumer;

/**
 * LinkedList models a simple linked list.
 *
 * @param <T> - The type of the list.
 */
public class LinkedList<T> implements Iterable<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    /**
     * Returns an empty linked list.
     * @return - Empty list.
     * @param <T> - The list type.
     */
    public static <T> LinkedList<T> emptyList() {
        return new LinkedList<>();
    }

    /**
     * Builds a list of given values.
     * @param values the values
     * @return The linked list.
     * @param <T> - The value type
     */
    @SafeVarargs
    public static <T> LinkedList<T> of(T ...values) {
        var list = new LinkedList<T>();
        for (T value : values) {
            list.append(value);
        }
        return list;
    }

    /**
     * Returns the head of the list.
     */
    public Optional<Node<T>> getFirst() {
        if (head == null) {
            return Optional.empty();
        }
        return Optional.of(head);
    }

    /**
     * Returns the tail of the list.
     */
    public Optional<Node<T>> getLast() {
        if (tail == null) {
            return Optional.empty();
        }
        return Optional.of(tail);
    }

    /**
     * Appends the value to the list.
     *
     * @param value - The value to append.
     */
    public void append(T value) {
        var newNode = Node.of(value);
        if (head == null) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size += 1;
    }

    /**
     * Appends the values to the list.
     *
     * @param values - The value to append.
     */
    @SafeVarargs
    public final void append(T... values) {
        for (T value : values) {
            append(value);
        }
    }

    /**
     * Insert value at position in the list.
     * @param value - The value.
     * @param position - The position.
     */
    public void insert(T value, int position) {
        if (position < 0 || position > size) {
            throw new IllegalArgumentException("invalid position given");
        }
        // handle insert last
        if (position == size) {
            append(value);
            return;
        }
        // handle insert first
        if (position == 0) {
            var newNode = Node.of(value);
            newNode.setNext(head);
            head = newNode;
            size += 1;
            return;
        }
        // handle insert at position
        var currentPosition = 0;
        var currentNode = head;
        Node<T> previousNode = null;
        // search for position to insert at
        while (true) {
            if (currentPosition == position) {
                var newNode = Node.of(value);
                newNode.setNext(currentNode);
                previousNode.setNext(newNode);
                size += 1;
                break;
            }
            currentPosition += 1;
            previousNode = currentNode;
            currentNode = currentNode.getNext();
        }
    }

    /**
     * Returns the size of the list.
     *
     * @return The list size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the element at the given position.
     * @param position - The position
     * @return - The element.
     */
    public Optional<Node<T>> get(int position) {
        var currentPosition = 0;
        var currentNode = head;
        while (currentPosition <= position && currentNode != null) {
            if (currentPosition == position) {
                return Optional.of(currentNode);
            }
            currentNode = currentNode.getNext();
            currentPosition += 1;
        }
        return Optional.empty();
    }

    /**
     * Deletes an element at the given position.
     * @param position - The position
     */
    public void delete(int position) {
        if (position < 0 || position > size) {
            throw new IllegalArgumentException("invalid position given");
        }
        // Delete head
        if (position == 0) {
            head = head.getNext();
            // if the list size is 1 then we don't have elements anymore
            if (size == 1) {
                tail = null;
            }
            size -= 1;
            return;
        }
        // delete anything from tail
        var currentNode = head;
        Node<T> previousNode = null;
        var currentPosition = 0;
        while (true) {
            previousNode = currentNode;
            currentNode = currentNode.getNext();
            currentPosition += 1;
            // we found element at position N which is about to get deleted
            if (currentPosition == position) {
                previousNode.setNext(currentNode.getNext());

                // we deleted the tail, so we need to update tail var.
                if (currentPosition == size - 1) {
                    tail = previousNode;
                }
                break;
            }
        }
        size -= 1;
    }

    /**
     * Converts the LinkedList to a List.
     * @return - The List.
     */
    public List<T> toList() {
        var list = new ArrayList<T>();

        var currentNode = head;
        while (currentNode != null) {
            list.add(currentNode.value());
            currentNode = currentNode.getNext();
        }

        return list;
    }

    /**
     * Converts the LinkedList to an array.
     * @return The array
     */
    public T[] toArray() {
        var array = new Object[getSize()];

        var currentNode = head;
        var index = 0;
        while (currentNode != null) {
            array[index] = currentNode.value();
            currentNode = currentNode.getNext();
            index += 1;
        }

        return (T[]) array;
    }

    /**
     * Reverses the list in-place.
     */
    public void reverse() {
        if (size == 1) {
            return;
        }
        tail = head;
        var currentNode = head;
        Node<T> previousNode = null;
        var next = head;
        // we iterate through the list and updates next accordingly, until we reach the tail.
        while (next != null) {
            // save the next
            next = currentNode.getNext();
            // current node's next will be set to previous node.
            currentNode.setNext(previousNode);
            // track previous node by settings it to current node
            previousNode = currentNode;
            // track the current node by setting it to next
            currentNode = next;
        }
        // update the head
        head = previousNode;
    }

    @Override
    public Iterator<T> iterator() {
        return new NodeIterator<T>(this);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return Iterable.super.spliterator();
    }
}
