package kz.epam.jmp.dao.sql;

import kz.epam.jmp.dao.DaoException;
import kz.epam.jmp.dao.GenericDao;
import kz.epam.jmp.entity.BottleSize;
import kz.epam.jmp.entity.OrderContent;

import kz.epam.jmp.connection.ConnectionPool;
import kz.epam.jmp.entity.Water;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("contentDao")
public class OrderContentDao implements GenericDao<OrderContent> {

    private static final Logger LOGGER = Logger.getLogger(OrderContentDao.class);
    private ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void add(OrderContent content) throws DaoException {
        String sql = "INSERT INTO ORDER_CONTENT (WATER_ID, BOTTLE_SIZE_ID, QUANTITY, CUSTOMER_ORDER_ID) VALUES(?, ?, ?, ?)";
        addRemoveContent(content, sql);
    }

    @Override
    public void update(OrderContent content) throws DaoException {
        throw new DaoException("It's not allowed to update content!");
    }

    @Override
    public List<OrderContent> getAll() throws DaoException {
        Connection connection = pool.getConnection();
        List<OrderContent> contentList = new ArrayList<>();
        String sql = "SELECT ID, WATER_ID, BOTTLE_SIZE_ID, QUANTITY, CUSTOMER_ORDER_ID FROM ORDER_CONTENT";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            WaterDao waterDao = new WaterDao();
            BottleSizeDao bottleSizeDao = new BottleSizeDao();
            while (resultSet.next()) {
                OrderContent content = getContentFieldsFromDB(resultSet, waterDao, bottleSizeDao);
                contentList.add(content);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get all content SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return contentList;
    }

    public List<OrderContent> getAllByCustomerOrderId(int orderId) throws DaoException {
        Connection connection = pool.getConnection();
        OrderContent content;
        List<OrderContent> contentList = new ArrayList<>();
        String sql = "SELECT ID, WATER_ID, BOTTLE_SIZE_ID, QUANTITY, CUSTOMER_ORDER_ID FROM ORDER_CONTENT WHERE CUSTOMER_ORDER_ID=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            WaterDao waterDao = new WaterDao();
            BottleSizeDao bottleSizeDao = new BottleSizeDao();
            while (resultSet.next()) {
                content = getContentFieldsFromDB(resultSet, waterDao, bottleSizeDao);
                contentList.add(content);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get all content by order id SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return contentList;
    }

    @Override
    public OrderContent getById(int id) throws DaoException {
        Connection connection = pool.getConnection();
        OrderContent content = null;
        String sql = "SELECT ID, WATER_ID, BOTTLE_SIZE_ID, QUANTITY, CUSTOMER_ORDER_ID FROM ORDER_CONTENT WHERE ID=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            WaterDao waterDao = new WaterDao();
            BottleSizeDao bottleSizeDao = new BottleSizeDao();
            if (resultSet.next()) {
                content = getContentFieldsFromDB(resultSet, waterDao, bottleSizeDao);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get content by id SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return content;
    }

    @Override
    public void remove(OrderContent content) throws DaoException {
        String sql = "DELETE FROM ORDER_CONTENT WHERE ID=?";
        addRemoveContent(content, sql);
    }

    private void addRemoveContent(OrderContent content, String sqlParameter) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlParameter);
            if (content.getId() == null) {
                preparedStatement.setInt(1, content.getWater().getId());
                preparedStatement.setInt(2, content.getBottleSize().getId());
                preparedStatement.setInt(3, content.getQuantity());
                preparedStatement.setInt(4, content.getCustomerOrderId());
            } else preparedStatement.setInt(1, content.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Content creating SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
    }

    private OrderContent getContentFieldsFromDB(ResultSet resultSet, WaterDao waterDao, BottleSizeDao bottleSizeDao) throws SQLException, DaoException {
        OrderContent content;
        content = new OrderContent();
        content.setId(resultSet.getInt("ID"));
        int water_id = resultSet.getInt("WATER_ID");
        Water water = waterDao.getById(water_id);
        content.setWater(water);
        int bottle_size_id = resultSet.getInt("BOTTLE_SIZE_ID");
        BottleSize bottleSize = bottleSizeDao.getById(bottle_size_id);
        content.setBottleSize(bottleSize);
        content.setQuantity(resultSet.getInt("QUANTITY"));
        content.setCustomerOrderId(resultSet.getInt("CUSTOMER_ORDER_ID"));
        return content;
    }
}
