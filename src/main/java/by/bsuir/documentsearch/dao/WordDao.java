package by.bsuir.documentsearch.dao;

import by.bsuir.documentsearch.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class WordDao extends AbstractDao {
    private static final String INSERT_QUERY = "insert into word values (?, ?)";

    public WordDao() throws DaoException {
        super();
    }

    @Override
    public List get() throws DaoException {
        throw new DaoException("WordDao doesn't support this method");
    }

    @Override
    public void add(Object... parameters) throws DaoException {
        String word = (String) parameters[0];
        double b = (double) parameters[1];
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, word);
            statement.setDouble(2, b);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
