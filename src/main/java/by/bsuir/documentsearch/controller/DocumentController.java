package by.bsuir.documentsearch.controller;

import by.bsuir.documentsearch.entity.Document;
import by.bsuir.documentsearch.exception.ParserException;
import by.bsuir.documentsearch.exception.ServiceException;
import by.bsuir.documentsearch.parser.DocumentParser;
import by.bsuir.documentsearch.parser.HtmlPageParser;
import by.bsuir.documentsearch.service.DocumentService;
import by.bsuir.documentsearch.service.WordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DocumentController {
    private static final Logger logger = LogManager.getLogger(DocumentController.class);
    private static final String VENUS = "https://en.wikipedia.org/wiki/Venus";
    private static final String MARS = "https://en.wikipedia.org/wiki/Mars";
    private static final String[] URL = {VENUS, MARS};

    public void execute() {
        HtmlPageParser htmlPageParser = new HtmlPageParser();
        DocumentParser documentParser = new DocumentParser();
        try {
            DocumentService documentService = new DocumentService();
            List<Document> documents = documentService.get();
            for (String url : URL) {
                Document document = htmlPageParser.parse(url);
                document = documentParser.parse(document);
                documents.add(document);
            }
            WordService wordService = new WordService();
            wordService.add(documents);
        } catch (ServiceException | ParserException e) {
            logger.error(e);
        }
    }
}
