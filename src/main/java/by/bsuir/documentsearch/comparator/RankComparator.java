package by.bsuir.documentsearch.comparator;

import by.bsuir.documentsearch.entity.SearchResult;

import java.util.Comparator;

public class RankComparator implements Comparator<SearchResult> {
    @Override
    public int compare(SearchResult o1, SearchResult o2) {
        int result = 0;
        if (o1.getRank() < o2.getRank()) {
            result = -1;
        }
        if (o1.getRank() > o2.getRank()) {
            result = 1;
        }
        return result;
    }
}
