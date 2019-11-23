package by.bsuir.documentsearch.controller;

import by.bsuir.documentsearch.entity.Document;
import by.bsuir.documentsearch.entity.SearchResult;
import by.bsuir.documentsearch.exception.ControllerException;
import by.bsuir.documentsearch.exception.ParserException;
import by.bsuir.documentsearch.exception.ServiceException;
import by.bsuir.documentsearch.parser.DocumentParser;
import by.bsuir.documentsearch.service.DocumentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DocumentController implements Controller {
    private static final String QUESTION = "question";
    private static final String DOCUMENTS = "documents";

    @Override
    public void execute() {

    }

    @Override
    public void execute(HttpServletRequest request) throws ControllerException {
        DocumentParser parser = new DocumentParser();
        DocumentService service = new DocumentService();

        String question = request.getParameter(QUESTION);
        try {
            if (!question.equals("")) {
                Document documentQuestion = new Document();
                documentQuestion.setText(question);
                documentQuestion = parser.parse(documentQuestion);
                List<SearchResult> results = service.getSearch(documentQuestion);
                request.setAttribute(DOCUMENTS, results);
            }
        } catch (ParserException | ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
