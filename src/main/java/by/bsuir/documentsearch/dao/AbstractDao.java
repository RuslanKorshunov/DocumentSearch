package by.bsuir.documentsearch.dao;

import by.bsuir.documentsearch.exception.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class AbstractDao<T> {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/docsearch?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    Connection connection;

    public AbstractDao() throws DaoException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DaoException(e);
        }
    }

    public abstract List<T> getList() throws DaoException;

    public abstract Map<String, Double> getMap() throws DaoException;

    public abstract void add(Object... parameters) throws DaoException;
}
