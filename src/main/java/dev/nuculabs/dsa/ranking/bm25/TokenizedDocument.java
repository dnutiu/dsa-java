package dev.nuculabs.dsa.ranking.bm25;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TokenizedDocument {
    final private Document document;
    final private List<String> tokens = new ArrayList<>();

    private TokenizedDocument(Document document) {
        this.document = document;
        this.tokens.addAll(Arrays.stream(document.text().split(" "))
                .map(
                        i -> i.chars()
                                .filter(Character::isLetterOrDigit)
                                .mapToObj(j -> String.valueOf((char) j))
                                .collect(Collectors.joining())
                )
                .filter(i -> !i.isEmpty()).map(String::toLowerCase).collect(Collectors.toCollection(ArrayList::new)));
    }

    public static TokenizedDocument fromDocument(Document document) {
        return new TokenizedDocument(document);
    }

    /**
     * Returns the tokenized tokens
     * @return tokens list
     */
    public List<String> getTokens() {
        return tokens;
    }

    /**
     * Returns the document.
     */
    public Document getDocument() {
        return document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenizedDocument that = (TokenizedDocument) o;
        return Objects.equals(document, that.document);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(document);
    }
}
