package by.bsuir.documentsearch.service;

import by.bsuir.documentsearch.comparator.RankComparator;
import by.bsuir.documentsearch.dao.AbstractDao;
import by.bsuir.documentsearch.dao.DocumentDao;
import by.bsuir.documentsearch.entity.Document;
import by.bsuir.documentsearch.entity.SearchResult;
import by.bsuir.documentsearch.exception.DaoException;
import by.bsuir.documentsearch.exception.ParserException;
import by.bsuir.documentsearch.exception.ServiceException;
import by.bsuir.documentsearch.parser.DocumentParser;
import by.bsuir.documentsearch.parser.HtmlPageParser;
import by.bsuir.documentsearch.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DocumentService implements Service {
    private static final Logger logger = LogManager.getLogger(DocumentService.class);

    public List<Document> getDocuments() throws ServiceException {
        List<Document> documents;
        try {
            AbstractDao dao = new DocumentDao();
            documents = dao.getList();
            Parser htmlPageParser = new HtmlPageParser();
            Parser documentParser = new DocumentParser();
            for (Document document : documents) {
                String url = document.getUrl();
                Document documentTemp = htmlPageParser.parse(url);
                String title = documentTemp.getTitle();
                String text = documentTemp.getText();
                document.setTitle(title);
                document.setText(text);
                documentTemp = documentParser.parse(document);
                for (String word : documentTemp.getTerms()) {
                    int number = documentTemp.getNumber(word);
                    document.put(word, number);
                }
            }
        } catch (DaoException | ParserException e) {
            throw new ServiceException(e);
        }
        return documents;
    }

    public List<SearchResult> getSearch(Document documentQuestion) throws ServiceException {
        if (documentQuestion == null) {
            throw new ServiceException("document can't be null");
        }
        if (documentQuestion.size() == 0) {
            throw new ServiceException("documentQuestion can't be empty");
        }

        List<SearchResult> results = new ArrayList<>();
        WordService wordService = new WordService();

        Map<String, Double> dictionary = wordService.get();
        List<Document> documents = getDocuments();
        documents.add(documentQuestion);
        List<List<Double>> matrix = new ArrayList<>();
        for (int i = 0; i < documents.size(); i++) {
            List<Double> row = new ArrayList<>();
            matrix.add(row);
        }
        List<String> words = new ArrayList<>(dictionary.keySet());
        for (String word : words) {
            for (int i = 0; i < documents.size(); i++) {
                Document document = documents.get(i);
                double weight = 0;
                if (document.containsWord(word)) {
                    weight = document.getNumber(word) * dictionary.get(word);
                }
                matrix.get(i).add(weight);
            }
        }
        List<Double> cos = new ArrayList<>();
        double lengthQ = 0;
        for (double weight : matrix.get(matrix.size() - 1)) {
            lengthQ += weight * weight;
        }
        lengthQ = Math.sqrt(lengthQ);
        int questionIndex = documents.size() - 1;
        for (int i = 0; i < documents.size() - 1; i++) {
            double cosValue = 0;
            double weight = 0;
            double lengthD = 0;
            for (int j = 0; j < matrix.get(i).size(); j++) {
                double weightD = matrix.get(i).get(j);
                double weightQ = matrix.get(questionIndex).get(j);
                weight += weightD * weightQ;
                lengthD += weightD * weightD;
            }
            lengthD = Math.sqrt(lengthD);
            if (lengthQ != 0 && lengthD != 0) {
                cosValue = weight / (lengthD * lengthQ);
            }
            cos.add(cosValue);
        }
        for (int i = 0; i < cos.size(); i++) {
            double cosValue = cos.get(i);
            if (cosValue > 0) {
                SearchResult searchResult = new SearchResult();
                Document document = documents.get(i);
                searchResult.setRank(cosValue);
                String title = document.getTitle();
                searchResult.setTitle(title);
                long documentId = document.getDocumentID();
                searchResult.setDocumentID(documentId);
                String snippet = document.getText().substring(0, 200);
                searchResult.setSnippet(snippet);
                for (String word : documentQuestion.getTerms()) {
                    if (document.containsWord(word)) {
                        searchResult.addTerm(word);
                    }
                }
                results.add(searchResult);
            }
        }
        Collections.sort(results, new RankComparator());
        return results;
    }
}
