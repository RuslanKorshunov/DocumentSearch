package by.bsuir.documentsearch.entity;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private long documentID;
    private String title;
    private String snippet;
    private double rank;
    private String date;
    private List<String> terms;

    public SearchResult() {
        terms = new ArrayList<>();
    }

    public long getDocumentID() {
        return documentID;
    }

    public void setDocumentID(long documentID) {
        this.documentID = documentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addTerm(String term) {
        terms.add(term);
    }

    public List<String> getTerms() {
        return terms;
    }
}