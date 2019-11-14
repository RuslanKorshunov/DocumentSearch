package by.bsuir.documentsearch.service;

import by.bsuir.documentsearch.dao.AbstractDao;
import by.bsuir.documentsearch.dao.DocumentDao;
import by.bsuir.documentsearch.entity.Document;
import by.bsuir.documentsearch.exception.DaoException;
import by.bsuir.documentsearch.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DocumentService implements Service {
    private static final Logger logger = LogManager.getLogger(DocumentService.class);

    public List<Document> get() throws ServiceException {
        List<Document> documents;
        try {
            AbstractDao dao = new DocumentDao();
            documents = dao.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return documents;
    }
}
