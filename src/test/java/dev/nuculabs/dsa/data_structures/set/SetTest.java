package dev.nuculabs.dsa.data_structures.set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

class TestPerson {
    private final String name;

    public TestPerson(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        if (name.equals("Denis")) {
            return 2;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "TestPerson{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestPerson that = (TestPerson) o;
        return Objects.equals(name, that.name);
    }
}


public class SetTest {
    @Test
    public void testInsertElement() {
        // Given
        var set = Set.<String>construct();

        // Then
        set.insert("SomeElement");
    }

    @Test
    public void testContainsElementTrue() {
        // Setup
        var set = Set.<String>construct();

        // Test
        set.insert("SomeElement");

        // Assert
        Assertions.assertTrue(set.contains("SomeElement"));
    }

    @Test
    public void testContainsElementFalse() {
        // Setup
        var set = Set.<String>construct();

        // Test
        set.insert("SomeElement");

        // Assert
        Assertions.assertFalse(set.contains("AnotherElement"));
    }

    @Test
    public void testContainsElementWithCollision() {
        // Setup
        var set = Set.<TestPerson>construct();

        // Test
        set.insert(new TestPerson("Denis"));
        set.insert(new TestPerson("Alex"));

        // Assert
        Assertions.assertTrue(set.contains(new TestPerson("Alex")));
        Assertions.assertFalse(set.contains(new TestPerson("Paul")));
    }

    @Test
    public void testGetValues() {
        // Setup
        var set = Set.<String>construct();

        // Test
        set.insert("SomeElement");
        set.insert("AnotherElement");

        // Assert
        Assertions.assertEquals(List.of("SomeElement", "AnotherElement"), set.getValues());
    }

    @Test
    public void testGetValuesWithCollision() {
        // Setup
        var set = Set.<TestPerson>construct();

        // Test
        set.insert(new TestPerson("Denis"));
        set.insert(new TestPerson("Alex"));
        set.insert(new TestPerson("Paul"));

        // Assert
        Assertions.assertEquals(List.of(new TestPerson("Denis"), new TestPerson("Alex"), new TestPerson("Paul")), set.getValues());

    }
}
