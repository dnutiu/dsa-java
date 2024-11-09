package dev.nuculabs.dsa.ranking.bm25;

import utils.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implements the BM25+ ranking algorithm.
 */
public class Bm25Plus {
    /**
     * The storage holds a mapping of document id -> document.
     */
    final private HashMap<Integer, TokenizedDocument> storage = new HashMap<>();

    /**
     * The term frequency index holds a mapping of term -> list of documents in which the term occurs.
     */
    final private HashMap<String, Set<Integer>> termFrequencyIndex = new HashMap<>();

    /**
     * The tuning parameters are used to tune the result of the algorithm.
     * <p>
     * These values were taken directly from the paper.
     */
    final private Double tuningParameterB = 0.3;
    final private Double tuningParameterK1 = 1.6;
    final private Double tuningParameterDelta = 0.7;
    private Integer totalTokens = 0;
    private Double meanDocumentLengths = 0.0;

    /**
     * Returns the index size.
     */
    public int getIndexSize() {
        return storage.size();
    }

    /**
     * Indexes a document
     */
    public void index(Document document) {
        // Tokenize the document, for educational purposes and simplicity we will consider tokens only
        // the words delimited by a space and transform them into lowercase.
        TokenizedDocument tokenizedDocument = TokenizedDocument.fromDocument(document);

        // Document does not exist in index
        if (!storage.containsKey(document.documentId())) {
            storage.put(document.documentId(), tokenizedDocument);

            totalTokens += tokenizedDocument.getTokens().size();
            meanDocumentLengths = (double) totalTokens / storage.size();

            // Index all tokens
            for (String token : tokenizedDocument.getTokens()) {
                if (termFrequencyIndex.containsKey(token.toLowerCase())) {
                    termFrequencyIndex.get(token.toLowerCase()).add(document.documentId());
                } else {
                    Set<Integer> documentIds = new HashSet<>();
                    documentIds.add(document.documentId());
                    termFrequencyIndex.put(token.toLowerCase(), documentIds);
                }
            }
        }
    }

    /**
     * Indexes all documents.
     *
     * @param documents - The documents.
     */
    public void indexAll(Document... documents) {
        Arrays.stream(documents).forEach(this::index);
    }

    /**
     * Executes a term query against the index and ranks the results using bm25+.
     *
     * @param term - The term
     */
    public List<Pair<Double, Document>> termQuery(String term) {
        Set<Integer> documentIds = termFrequencyIndex.get(term.toLowerCase());
        if (documentIds == null) {
            return Collections.emptyList();
        }

        List<Pair<Double, Document>> results = new ArrayList<>();

        for (Integer id : documentIds) {
            TokenizedDocument document = storage.get(id);
            if (document == null) {
                continue;
            }
            double documentRsv = computeRsv(term.toLowerCase(), document);
            results.add(new Pair<>(documentRsv, document.getDocument()));
        }

        results.removeIf(entry -> !Double.isFinite(entry.first()));
        results.sort((a, b) -> Double.compare(b.first(), a.first()));

        return results;
    }

    /**
     * Executes a terms query against the index and ranks the results using bm25+.
     *
     * @param terms - The terms
     */
    public List<Pair<Double, Document>> termsQuery(String... terms) {
        var documentIds = Arrays.stream(terms).map(term -> termFrequencyIndex.getOrDefault(term.toLowerCase(), Set.of())).reduce((acc, value) -> {
            acc.addAll(value);
            return acc;
        }).orElse(Set.of());

        var results = documentIds.stream().map(i -> {
            var document = storage.get(i);

            // Sum the RSV of each term.
            double rsvSum = 0;
            for (String term : terms) {
                rsvSum += computeRsv(term, document);
            }

            return new Pair<>(rsvSum, document.getDocument());
        }).collect(Collectors.toCollection(ArrayList::new));

        results.removeIf(entry -> !Double.isFinite(entry.first()));
        results.sort((a, b) -> Double.compare(b.first(), a.first()));

        return results;
    }

    /**
     * Computes the inverse document frequency for a given term.
     * <p>
     * The IDF is defined as the total number of documents (N) divided by the documents that contain the term (dft).
     * In the BM25+ version the IDF is the (N+1)/(dft)
     */
    private double computeInverseDocumentFrequency(String term) {
        int numberOfDocumentsContainingTheTerm = termFrequencyIndex.containsKey(term) ? termFrequencyIndex.get(term).size() : 0;
        return (storage.size() + 1) / (double) numberOfDocumentsContainingTheTerm;
    }

    /**
     * Computes the RSV for the given term and document.
     * The RSV (Retrieval Status Value) is computed for every document using the BM25+ formula from the paper.
     */
    private double computeRsv(String term, TokenizedDocument document) {
        double inverseDocumentFrequencyLog = Math.log10(computeInverseDocumentFrequency(term));
        double termOccurringInDocumentFrequency = (double) document.getTokens().stream()
                .filter(token -> token.equals(term))
                .count();
        double documentLength = document.getTokens().size();

        return inverseDocumentFrequencyLog *
                (((tuningParameterK1 + 1) * termOccurringInDocumentFrequency) /
                        ((tuningParameterK1 * ((1 - tuningParameterB) + tuningParameterB * (documentLength / meanDocumentLengths))) + termOccurringInDocumentFrequency)
                        + tuningParameterDelta);
    }
}
