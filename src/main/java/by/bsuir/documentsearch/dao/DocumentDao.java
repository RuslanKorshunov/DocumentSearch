package by.bsuir.documentsearch.dao;

import by.bsuir.documentsearch.entity.Document;
import by.bsuir.documentsearch.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentDao extends AbstractDao<Document> {
    private static final String SELECT_ALL_QUERY = "select * from document";

    public DocumentDao() throws DaoException {
        super();
    }

    @Override
    public List<Document> get() throws DaoException {
        List<Document> documents = new ArrayList<>();
        try (
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Document document = new Document();
                int id = resultSet.getInt(1);
                String url = resultSet.getString(2);
                document.setDocumentID(id);
                document.setUrl(url);
                documents.add(document);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return documents;
    }

    @Override
    public void add(Object... parameters) throws DaoException {
        throw new DaoException("DocumentDao doesn't support this method");
    }
}
