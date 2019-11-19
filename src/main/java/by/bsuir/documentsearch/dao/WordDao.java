package by.bsuir.documentsearch.dao;

import by.bsuir.documentsearch.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordDao extends AbstractDao {
    private static final String INSERT_QUERY = "insert into word values (?, ?)";
    private static final String SELECT_ALL_QUERY = "select * from word";

    public WordDao() throws DaoException {
        super();
    }

    @Override
    public List getList() throws DaoException {
        throw new DaoException("WordDao doesn't support this method");
    }

    @Override
    public Map<String, Double> getMap() throws DaoException {
        Map<String, Double> dictionary = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String word = resultSet.getString(1);
                double b = resultSet.getDouble(2);
                dictionary.put(word, b);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return dictionary;
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
