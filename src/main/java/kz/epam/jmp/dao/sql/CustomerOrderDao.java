package kz.epam.jmp.dao.sql;

import kz.epam.jmp.dao.DaoException;
import kz.epam.jmp.dao.GenericDao;
import kz.epam.jmp.entity.CustomerOrder;
import kz.epam.jmp.connection.ConnectionPool;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("orderDao")
public class CustomerOrderDao implements GenericDao<CustomerOrder> {

    private static final Logger LOGGER = Logger.getLogger(CustomerOrderDao.class);
    private ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void add(CustomerOrder order) throws DaoException {
        String sql = "INSERT INTO CUSTOMER_ORDER (CUSTOMER_ID, AMOUNT, ORDER_DATE, STATUS) VALUES(?, ?, ?, ?)";
        addUpdateOrder(order, sql);
    }

    @Override
    public void update(CustomerOrder order) throws DaoException {
        String sql = "UPDATE CUSTOMER_ORDER SET CUSTOMER_ID=?, AMOUNT=?, ORDER_DATE=?, STATUS=? WHERE ID=?";
        addUpdateOrder(order, sql);
    }

    @Override
    public List<CustomerOrder> getAll() throws DaoException {
        Connection connection = pool.getConnection();
        List<CustomerOrder> orderList = new ArrayList<>();
        String sql = "SELECT ID, CUSTOMER_ID, AMOUNT, ORDER_DATE, STATUS FROM CUSTOMER_ORDER";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CustomerOrder order = getCustomerOrderFieldsFromDB(resultSet);
                orderList.add(order);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get all orders SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return orderList;
    }

    public List<CustomerOrder> getAllByClientId(int id) throws DaoException {
        Connection connection = pool.getConnection();
        CustomerOrder order;
        List<CustomerOrder> orderList = new ArrayList<>();
        String sql = "SELECT ID, CUSTOMER_ID, AMOUNT, ORDER_DATE, STATUS FROM CUSTOMER_ORDER WHERE CUSTOMER_ID=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                order = new CustomerOrder();
                order.setId(resultSet.getInt("ID"));
                order.setCustomerId(resultSet.getInt("CUSTOMER_ID"));
                order.setAmount(resultSet.getDouble("AMOUNT"));
                order.setOrderDate(resultSet.getDate("ORDER_DATE"));
                order.setStatus(CustomerOrder.Status.valueOf(resultSet.getString("STATUS")));
                orderList.add(order);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get all orders by client id SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return orderList;
    }

    @Override
    public CustomerOrder getById(int id) throws DaoException {
        String sql = "SELECT ID, CUSTOMER_ID, AMOUNT, ORDER_DATE, STATUS FROM CUSTOMER_ORDER WHERE ID=?";
        return getByParameter(id, sql);
    }

    public CustomerOrder getCreatingOrderByUserId(int userId) throws DaoException {
        String sql = "SELECT ID, CUSTOMER_ID, AMOUNT, ORDER_DATE, STATUS FROM CUSTOMER_ORDER WHERE CUSTOMER_ID=? AND STATUS='CREATING'";
        return getByParameter(userId, sql);
    }

    public Integer getByUserId(int userId) throws DaoException {
        Connection connection = pool.getConnection();
        Integer order = null;
        String sql = "SELECT MAX(ID) FROM CUSTOMER_ORDER WHERE CUSTOMER_ID =?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = resultSet.getInt("MAX(ID)");
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get the last one order by user id SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return order;
    }

    @Override
    public void remove(CustomerOrder order) throws DaoException {
        throw new DaoException("It's not allowed to delete order!");
    }

    private void addUpdateOrder(CustomerOrder order, String sqlParameter) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlParameter);
            preparedStatement.setInt(1, order.getCustomerId());
            preparedStatement.setDouble(2, order.getAmount());
            preparedStatement.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            preparedStatement.setString(4, String.valueOf(order.getStatus()));
            if (order.getId() != null) preparedStatement.setInt(5, order.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Order creating and updating SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
    }

    private CustomerOrder getByParameter(Object parameter, String sqlParameter) throws DaoException {
        Connection connection = pool.getConnection();
        CustomerOrder order = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlParameter);
            preparedStatement.setObject(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = getCustomerOrderFieldsFromDB(resultSet);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get order by " + parameter + " id SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return order;
    }

    private CustomerOrder getCustomerOrderFieldsFromDB(ResultSet resultSet) throws SQLException {
        CustomerOrder order;
        order = new CustomerOrder();
        order.setId(resultSet.getInt("ID"));
        order.setCustomerId(resultSet.getInt("CUSTOMER_ID"));
        order.setAmount(resultSet.getDouble("AMOUNT"));
        order.setOrderDate(resultSet.getDate("ORDER_DATE"));
        order.setStatus(CustomerOrder.Status.valueOf(resultSet.getString("STATUS")));
        return order;
    }
}
