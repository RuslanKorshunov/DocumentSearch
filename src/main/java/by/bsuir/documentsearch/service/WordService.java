package by.bsuir.documentsearch.service;

import by.bsuir.documentsearch.dao.AbstractDao;
import by.bsuir.documentsearch.dao.WordDao;
import by.bsuir.documentsearch.entity.Document;
import by.bsuir.documentsearch.exception.DaoException;
import by.bsuir.documentsearch.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordService implements Service {
    private static final Logger logger = LogManager.getLogger(WordService.class);

    public void add(List<Document> documents) throws ServiceException{
        Map<String, WordInformation> dictionary = createDictionary(documents);
        try {
            AbstractDao dao = new WordDao();
            for (Map.Entry entry : dictionary.entrySet()) {
                String word = (String) entry.getKey();
                WordInformation wordInformation = (WordInformation) entry.getValue();
                double b = wordInformation.b;
                if (b == 0) {
                    logger.info(word);
                }
                dao.add(word, b);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
//        for (Map.Entry entry : dictionary.entrySet()) {
//            String word = (String) entry.getKey();
//            for (Document document : documents) {
//                if (document.containsWord(word)) {
//                    WordInformation wordInformation = (WordInformation) entry.getValue();
//                    int number = document.getNumber(word);
//                    double q = (double) number / document.size();
//                    double b = wordInformation.b;
//                    double weight = q * b;
//                    logger.info(document.getTitle() + "\n size: " + document.size() +
//                            "\n word: " + word + "\n number: " + number + "\n q: " + q + "\n weight:" + weight);
//                }
//            }
//        }
    }

    public Map<String, Double> get() throws ServiceException {
        Map<String, Double> dictionary;
        try {
            AbstractDao dao = new WordDao();
            dictionary = dao.getMap();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return dictionary;
    }

    private Map<String, WordInformation> createDictionary(List<Document> documents) {
        Map<String, WordInformation> dictionary = new HashMap<>();
        int documentsSize = documents.size();
        for (Document document : documents) {
            Set<String> terms = document.getTerms();
            for (String term : terms) {
                if (dictionary.containsKey(term)) {
                    WordInformation wordInformation = dictionary.get(term);
                    int number = wordInformation.numberDocuments + 1;
                    wordInformation.setNumberDocuments(number);
                    dictionary.put(term, wordInformation);
                } else {
                    WordInformation wordInformation = new WordInformation(documentsSize);
                    wordInformation.setNumberDocuments(1);
                    dictionary.put(term, wordInformation);
                }
            }
        }
        return dictionary;
    }

    private static class WordInformation {
        private int numberDocuments;
        private double b;
        private int numberDocumentsAll;

        WordInformation(int numberDocumentsAll) {
            numberDocuments = 0;
            b = 0;
            this.numberDocumentsAll = numberDocumentsAll;
        }

        public int getNumberDocuments() {
            return numberDocuments;
        }

        public void setNumberDocuments(int numberDocuments) {
            if (numberDocuments <= numberDocumentsAll) {
                this.numberDocuments = numberDocuments;
                b = Math.log((double) numberDocumentsAll / numberDocuments);
            }
        }

        public double getB() {
            return b;
        }

        @Override
        public String toString() {
            return "WordInformation{" +
                    "numberDocuments=" + numberDocuments +
                    ", b=" + b +
                    ", numberDocumentsAll=" + numberDocumentsAll +
                    '}';
        }
    }
}
