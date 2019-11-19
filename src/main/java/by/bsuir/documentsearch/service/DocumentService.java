package by.bsuir.documentsearch.service;

import by.bsuir.documentsearch.dao.AbstractDao;
import by.bsuir.documentsearch.dao.DocumentDao;
import by.bsuir.documentsearch.entity.Document;
import by.bsuir.documentsearch.exception.DaoException;
import by.bsuir.documentsearch.exception.ParserException;
import by.bsuir.documentsearch.exception.ServiceException;
import by.bsuir.documentsearch.parser.DocumentParser;
import by.bsuir.documentsearch.parser.HtmlPageParser;
import by.bsuir.documentsearch.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DocumentService implements Service {
    private static final Logger logger = LogManager.getLogger(DocumentService.class);

    public List<Document> get() throws ServiceException {
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
}
