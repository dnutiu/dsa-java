package dev.nuculabs.dsa.data_structures.linked_list;

/**
 * Node represents a linked list node.
 * @param <T> - The type of the Node's value.
 */
public class Node<T> {

    final private T value;
    private Node<T> next;

    /**
     * Constructs a Node instance.
     * @param value - The value of the node.
     * @param next - A pointer to the next node.
     */
    private Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }

    /**
     * Constructs a Node instance of a given value.
     * @param value - The node value
     * @return - The node instance.
     * @param <T> - The type of the node value.
     */
    public static <T> Node<T> of(T value) {
        return new Node<>(value, null);
    }

    /**
     * Sets the next pointer.
     * @param next - The node.
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }

    /**
     * Gets the next node in the list.
     * @return The node optional.
     */
    public Node<T> getNext() {
        return next;
    }

    /**
     * Returns the value of the node.
     * @return - The value.
     */
    public T value() {
        return this.value;
    }
}
