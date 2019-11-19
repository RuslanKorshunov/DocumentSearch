package by.bsuir.documentsearch.controller;

import by.bsuir.documentsearch.entity.Document;
import by.bsuir.documentsearch.exception.ParserException;
import by.bsuir.documentsearch.exception.ServiceException;
import by.bsuir.documentsearch.parser.DocumentParser;
import by.bsuir.documentsearch.service.DocumentService;
import by.bsuir.documentsearch.service.WordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DocumentController implements Controller {
    private static final Logger logger = LogManager.getLogger(DocumentController.class);
    private static final String QUESTION = "question";

    @Override
    public void execute() {

    }

    @Override
    public void execute(HttpServletRequest request) {
        DocumentParser parser = new DocumentParser();

        String question = request.getParameter(QUESTION);
        try {
            if (!question.equals("")) {
                Document documentQuestion = new Document();
                documentQuestion.setText(question);
                documentQuestion = parser.parse(documentQuestion);
                WordService wordService = new WordService();
                Map<String, Double> dictionary = wordService.get();
                DocumentService documentService = new DocumentService();
                List<Document> documents = documentService.get();
                documents.add(documentQuestion);
                List<List<Double>> matrix = new ArrayList<>();
                for (Document document : documents) {
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
            }
        } catch (ParserException | ServiceException e) {
            logger.error(e);
        }
    }
}
