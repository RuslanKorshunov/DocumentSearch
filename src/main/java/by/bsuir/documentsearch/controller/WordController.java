package by.bsuir.documentsearch.controller;

import by.bsuir.documentsearch.entity.Document;
import by.bsuir.documentsearch.exception.ServiceException;
import by.bsuir.documentsearch.service.DocumentService;
import by.bsuir.documentsearch.service.WordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class WordController implements Controller {
    private static final Logger logger = LogManager.getLogger(WordController.class);

    public void execute() {
        try {
            DocumentService documentService = new DocumentService();
            List<Document> documents = documentService.getDocuments();
            WordService wordService = new WordService();
            wordService.add(documents);
        } catch (ServiceException e) {
            logger.error(e);
        }
    }

    @Override
    public void execute(HttpServletRequest request) {

    }
}