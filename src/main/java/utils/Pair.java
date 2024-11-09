package utils;

/**
 * Represents a simple pair.
 * @param first - The first item from the pair.
 * @param second - The second item from the pair
 * @param <T> - The type of the first item.
 * @param <V> - The type of the second item.
 */
public record Pair<T, V>(T first, V second) { }
