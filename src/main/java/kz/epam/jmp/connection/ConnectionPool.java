package kz.epam.jmp.connection;

import kz.epam.jmp.entity.Entity;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    BlockingQueue<Connection> connections = new ArrayBlockingQueue<>(Entity.POOL_SIZE);

    public ConnectionPool() {
        initializeConnectionPool();
    }

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        return Entity.INSTANCE;
    }

    public void initializeConnectionPool() throws ConnectionPoolException {
        while ((connections.size() < Entity.POOL_SIZE)) {
            try {
                Class.forName(Entity.DRIVER);
            } catch (ClassNotFoundException e) {
                LOGGER.error("ClassNotFoundException in initializeConnectionPool", e);
            }
            Connection connection;
            try {
                connection = DriverManager.getConnection(Entity.URL, Entity.LOGIN, Entity.PASSWORD);
            } catch (SQLException e) {
                LOGGER.error("SQLException in initializeConnectionPool", e);
                throw new ConnectionPoolException(e);
            }
            connections.add(connection);
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            LOGGER.warn("InterruptedException in getConnection", e);
            throw new ConnectionPoolException(e);
        }
        return connection;
    }

    public synchronized void returnConnection(Connection connection) {
        connections.add(connection);
    }

    public synchronized void closeConnections() {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("SQLException in closeConnections", e);
            }
        }
    }
}
