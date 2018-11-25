package ru.javaops.webapp.sql;

import ru.javaops.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void executePreparedStatement(String sql, StatementExecutor statementExecutor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            statementExecutor.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T executePreparedStatement(String sql, StatementFunction<T> statementFunction) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return statementFunction.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface StatementExecutor {
        void execute(PreparedStatement preparedStatement) throws SQLException;
    }

    public interface StatementFunction<T> {
        T execute(PreparedStatement preparedStatement) throws SQLException;
    }
}
