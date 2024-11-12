package dev.nuculabs.dsa.data_structures.set;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Set is a set of items with simple operations.
 *
 * @param <T>
 */
public class Set<T> {
    private final ArrayList<T>[] hashTable;
    private final int capacity;
    private final ArrayList<Integer> values;

    @SuppressWarnings("unchecked")
    private Set(int capacity) {
        hashTable = new ArrayList[capacity];
        values = new ArrayList<>();
        this.capacity = capacity;
    }

    /**
     * Insets an element into the set.
     *
     * @param element - The element.
     */
    public void insert(T element) {
        if (this.contains(element)) {
            return;
        }
        var hash = getHash(element);
        var list = hashTable[hash];
        if (list == null) {
            hashTable[hash] = new ArrayList<T>();
            hashTable[hash].add(element);

            values.add(hash);
        } else {
            list.add(element);
        }
    }

    /**
     * Checks if the element is present in the set.
     *
     * @param element - The element.
     */
    public boolean contains(T element) {
        var list = hashTable[getHash(element)];
        if (list != null) {
            return list.contains(element);
        } else {
            return false;
        }
    }

    /**
     * Returns all the values from the set.
     *
     * @return A list of values.
     */
    public List<T> getValues() {
        return this.values.stream().flatMap(hash -> this.hashTable[hash].stream()).collect(Collectors.toList());
    }

    private int getHash(T element) {
        return Math.abs(element.hashCode()) % capacity;
    }

    /**
     * Constructs a new Set<T> of given capacity.
     *
     * @param capacity - The capacity of the set.
     * @param <T>      - The type of the set.
     * @return - The set object.
     */
    public static <T> Set<T> of(int capacity) {
        return new Set<>(capacity);
    }

    /**
     * Constructs a new Set<T> with a default capacity.
     *
     * @param <T> - The type of the set.
     * @return - The set object.
     */
    public static <T> Set<T> construct() {
        return new Set<>(1024);
    }
}
