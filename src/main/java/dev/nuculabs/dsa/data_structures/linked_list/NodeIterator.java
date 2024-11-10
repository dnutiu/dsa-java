package dev.nuculabs.dsa.data_structures.linked_list;

import java.util.Iterator;

public class NodeIterator<T> implements Iterator<T> {
    private Node<T> currentNode;

    public NodeIterator(LinkedList<T> linkedList) {
        currentNode = linkedList.getFirst().orElse(null);
    }

    @Override
    public boolean hasNext() {
        return currentNode != null;
    }

    @Override
    public T next() {
        var node = currentNode;
        currentNode = currentNode.getNext();
        return node.value();
    }
}
