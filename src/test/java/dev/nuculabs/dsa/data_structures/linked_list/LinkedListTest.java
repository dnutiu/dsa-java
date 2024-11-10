package dev.nuculabs.dsa.data_structures.linked_list;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class LinkedListTest {


    @Test
    public void test_getFirst_EmptyList() {
        // Setup
        var linkedList = new LinkedList<String>();

        // Test
        Assertions.assertTrue(linkedList.getFirst().isEmpty());
    }

    @Test
    public void test_getFirst_empty() {
        // Setup
        var linkedList = LinkedList.emptyList();

        // Test
        Assertions.assertTrue(linkedList.getFirst().isEmpty());
    }

    @Test
    public void test_getFirst() {
        // Setup
        var linkedList = new LinkedList<String>();
        linkedList.append("First");
        linkedList.append("Second");
        linkedList.append("Third");

        // Test
        Assertions.assertEquals("First", linkedList.getFirst().orElseThrow().value());
    }

    @Test
    public void test_getFirst_of() {
        // Setup
        var linkedList = LinkedList.of("First", "Second", "Third");

        // Test
        Assertions.assertEquals("First", linkedList.getFirst().orElseThrow().value());
    }

    @Test
    public void test_getLast() {
        // Setup
        var linkedList = new LinkedList<String>();
        linkedList.append("First");
        linkedList.append("Second");
        linkedList.append("Third");

        // Test
        Assertions.assertEquals("Third", linkedList.getLast().orElseThrow().value());
    }

    @Test
    public void test_get() {
        // Setup
        var linkedList = new LinkedList<String>();
        linkedList.append("First");
        linkedList.append("Second");
        linkedList.append("Third");

        // Test
        Assertions.assertEquals("First", linkedList.get(0).orElseThrow().value());
        Assertions.assertEquals("Second", linkedList.get(1).orElseThrow().value());
        Assertions.assertEquals("Third", linkedList.get(2).orElseThrow().value());
        Assertions.assertTrue(linkedList.get(3).isEmpty());
    }


    @Test
    public void test_deleteHead() {
        var linkedList = new LinkedList<String>();
        linkedList.append("First");
        linkedList.append("Second");
        linkedList.append("Third");

        // Test
        linkedList.delete(0);

        // Assert
        Assertions.assertEquals("Second", linkedList.getFirst().orElseThrow().value());
        Assertions.assertEquals("Third", linkedList.getLast().orElseThrow().value());
    }

    @Test
    public void test_deleteMiddle() {
        var linkedList = new LinkedList<String>();
        linkedList.append("First");
        linkedList.append("Second");
        linkedList.append("Third");

        // Test
        linkedList.delete(1);

        // Assert
        Assertions.assertEquals("First", linkedList.getFirst().orElseThrow().value());
        Assertions.assertEquals("Third", linkedList.getLast().orElseThrow().value());
    }

    @Test
    public void test_deleteLast() {
        var linkedList = new LinkedList<String>();
        linkedList.append("First");
        linkedList.append("Second");
        linkedList.append("Third");

        // Test
        linkedList.delete(2);

        // Assert
        Assertions.assertEquals("First", linkedList.getFirst().orElseThrow().value());
        Assertions.assertEquals("Second", linkedList.getLast().orElseThrow().value());
    }

    @Test
    public void test_deleteInvalidPosition() {
        // Setup
        var linkedList = new LinkedList<String>();
        linkedList.append("First");

        // Test
        Assertions.assertThrows(IllegalArgumentException.class, () -> linkedList.delete(-1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> linkedList.delete(99));
    }

    @Test
    public void test_insertFirst() {
        // Setup
        var linkedList = new LinkedList<String>();

        // Test
        linkedList.insert("First", 0);

        // Assert
        Assertions.assertEquals(1, linkedList.getSize());
        Assertions.assertEquals("First", linkedList.get(0).get().value());
    }

    @Test
    public void test_insertLast() {
        // Setup
        var linkedList = new LinkedList<String>();
        linkedList.insert("First", 0);

        // Test
        linkedList.insert("Second", linkedList.getSize());

        // Assert
        Assertions.assertEquals(2, linkedList.getSize());
        Assertions.assertEquals("Second", linkedList.get(1).get().value());
    }

    @Test
    public void test_insertMiddle() {
        // Setup
        var linkedList = new LinkedList<String>();
        linkedList.insert("First", 0);
        linkedList.insert("Last", linkedList.getSize());

        // Test
        linkedList.insert("Middle", linkedList.getSize() - 1);

        // Assert
        Assertions.assertEquals(3, linkedList.getSize());
        Assertions.assertEquals("First", linkedList.get(0).get().value());
        Assertions.assertEquals("Middle", linkedList.get(1).get().value());
        Assertions.assertEquals("Last", linkedList.get(2).get().value());
    }

    @Test
    public void test_toList() {
        // Given
        var list = LinkedList.of("One", "Two", "Three");

        // Then
        Assertions.assertEquals(List.of("One", "Two", "Three"), list.toList());
    }

    @Test
    public void test_toArray() {
        // Given
        var list = LinkedList.of("One", "Two", "Three");

        // Then
        Assertions.assertArrayEquals(List.of("One", "Two", "Three").toArray(), list.toArray());
    }


    @Test
    public void test_reverseOneElement() {
        // Setup
        var linkedList = new LinkedList<String>();
        linkedList.append("First");

        // Test
        linkedList.reverse();

        // Assert
        Assertions.assertEquals(List.of("First"), linkedList.toList());
    }

    @Test
    public void test_reverseTwoElement() {
        // Setup
        var linkedList = new LinkedList<String>();
        linkedList.append("First", "Second");

        // Test
        linkedList.reverse();

        // Assert
        Assertions.assertEquals(List.of("Second", "First"), linkedList.toList());
    }

    @Test
    public void test_reverseFiveElement() {
        // Setup
        var linkedList = new LinkedList<String>();
        linkedList.append("First", "Second", "Third", "Fourth");

        // Test
        linkedList.reverse();

        // Assert
        Assertions.assertEquals(List.of("Fourth", "Third", "Second", "First"), linkedList.toList());
    }

    @Test
    public void test_linkedList_iteration() {
        // Setup
        var linkedList = LinkedList.of("First", "Second", "Third", "Fourth");
        Iterable<String> iterable = linkedList::iterator;
        Stream<String> targetStream = StreamSupport.stream(iterable.spliterator(), false);

        // Test
        var resultingList = targetStream.map(i -> String.format("%sX", i)).collect(Collectors.toCollection(ArrayList::new));

        // Assert
        Assertions.assertEquals(List.of("FirstX", "SecondX", "ThirdX", "FourthX"), resultingList);
    }
}
