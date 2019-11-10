package by.bsuir.documentsearch.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Document {
    private long documentID;
    private String title;
    private String text;
    private String url;
    private Map<String, Integer> terms;

    public Document() {
        documentID = 0;
        title = "";
        text = "";
        url = "";
        terms = new HashMap<>();
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<String> getTerms() {
        return terms.keySet();
    }

    public Integer getNumber(String number) {
        return terms.get(number);
    }

    public Integer put(String word, Integer number) {
        return terms.put(word, number);
    }

    public boolean containsWord(String word) {
        return terms.containsKey(word);
    }



    public int size() {
        int size = 0;
        for (Map.Entry entry : terms.entrySet()) {
            size += (Integer) entry.getValue();
        }
        return size;
    }
}