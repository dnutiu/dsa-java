package dev.nuculabs.dsa.ranking;

import dev.nuculabs.dsa.ranking.bm25.Bm25Plus;
import dev.nuculabs.dsa.ranking.bm25.Document;
import utils.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Bm25PlusTest {
    @Test
    public void test_index_and_indexSize() {
        // Setup
        Bm25Plus bm25Plus = new Bm25Plus();

        Document document1 = new Document(1, "Ana are mere");
        Document document2 = new Document(2, "Ana Ana Ana Ana Ana Ana Ana Ana");

        // Test
        bm25Plus.indexAll(document1, document2);

        // Assert
        Assertions.assertEquals(2, bm25Plus.getIndexSize());
    }

    @Test
    public void test_termQuery() {
        // Given
        Bm25Plus bm25Plus = new Bm25Plus();

        Document document1 = new Document(1, "Ana are mere");
        Document document2 = new Document(2, "Ana Ana Ana Ana Ana Ana Ana Ana");
        bm25Plus.indexAll(document1, document2);

        // Then
        Assertions.assertEquals(
                new ArrayList<>(Arrays.asList(
                        new Pair<>(0.4963164745976794, document2),
                        new Pair<>(0.3154856374073922, document1)
                )),
                bm25Plus.termQuery("Ana")
        );

        Assertions.assertEquals(
                new ArrayList<>(List.of(
                        new Pair<>(0.8548118968145402, document1)
                )),
                bm25Plus.termQuery("mere")
        );

        Assertions.assertEquals(
                Collections.emptyList(),
                bm25Plus.termQuery("batman")
        );

        Assertions.assertEquals(
                new ArrayList<>(Arrays.asList(
                        new Pair<>(0.4963164745976794, document2),
                        new Pair<>(0.3154856374073922, document1)
                )),
                bm25Plus.termQuery("ana")
        );
    }

    @Test
    public void test_termsQuery() {
        // Given
        Bm25Plus bm25Plus = new Bm25Plus();

        Document document1 = new Document(1, "A linked list is a fundamental data structure which consists of Nodes that are connected to each other.");
        Document document2 = new Document(2, "The Linked List data structure permits the storage of data in an efficient manner.");
        Document document3 = new Document(3, "The space and time complexity of the linked list operations depends on the implementation.");
        Document document4 = new Document(4, "The operations that take O(N) time takes this much because you have to traverse the list’s for at least N nodes in order to perform it successfully. On the other hand, operations that take O(1) time do not require any traversals because the list holds pointers to the head first Node and tail last Node.");
        bm25Plus.indexAll(document1, document2, document3, document4);


        // Then
        Assertions.assertEquals(
                new ArrayList<>(List.of(
                        new Pair<>(1.5977607472650388, document3),
                        new Pair<>(0.8361444686814765, document2),
                        new Pair<>(0.8296222299960145, document1),
                        new Pair<>(0.704549447544239, document4)
                )),
                bm25Plus.termsQuery("linked", "list", "complexity")
        );
    }
}

