package kz.epam.jmp.dao.sql;

import kz.epam.jmp.dao.DaoException;
import kz.epam.jmp.dao.GenericDao;
import kz.epam.jmp.entity.BottleSize;
import kz.epam.jmp.connection.ConnectionPool;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("sizeDao")
public class BottleSizeDao implements GenericDao<BottleSize> {

    private static final Logger LOGGER = Logger.getLogger(BottleSizeDao.class);
    private ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void add(BottleSize bottle) throws DaoException {
        String sql = "INSERT INTO BOTTLE_SIZE (SIZE) VALUES(?)";
        addUpdateSize(bottle, sql);
    }

    @Override
    public void update(BottleSize bottle) throws DaoException {
        String sql = "UPDATE BOTTLE_SIZE SET SIZE=? WHERE ID=?";
        addUpdateSize(bottle, sql);
    }

    @Override
    public List<BottleSize> getAll() throws DaoException {
        Connection connection = pool.getConnection();
        List<BottleSize> bottleSizeList = new ArrayList<>();
        String sql = "SELECT ID, SIZE FROM BOTTLE_SIZE";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BottleSize bottle = getBottleSizeFieldsFromDB(resultSet);
                bottleSizeList.add(bottle);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get all bottle SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return bottleSizeList;
    }

    @Override
    public BottleSize getById(int id) throws DaoException {
        String sql = "SELECT ID, SIZE FROM BOTTLE_SIZE WHERE ID=?";
        return getByParameter(id, sql);
    }

    public BottleSize getBySize(double size) throws DaoException {
        String sql = "SELECT ID, SIZE FROM BOTTLE_SIZE WHERE SIZE=?";
        return getByParameter(size, sql);
    }

    @Override
    public void remove(BottleSize bottle) throws DaoException {
        throw new DaoException("It's not allowed to delete bottle!");
    }

    private void addUpdateSize(BottleSize bottle, String sqlParameter) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlParameter);
            preparedStatement.setDouble(1, bottle.getSize());
            if (bottle.getId() != null) preparedStatement.setInt(2, bottle.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Bottle creating and updating SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
    }

    private BottleSize getByParameter(Object parameter, String sqlParameter) throws DaoException {
        Connection connection = pool.getConnection();
        BottleSize bottle = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlParameter);
            preparedStatement.setObject(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bottle = getBottleSizeFieldsFromDB(resultSet);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get bottle by " + parameter + " SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return bottle;
    }

    private BottleSize getBottleSizeFieldsFromDB(ResultSet resultSet) throws SQLException {
        BottleSize bottle = new BottleSize();
        bottle.setId(resultSet.getInt("ID"));
        bottle.setSize(resultSet.getDouble("SIZE"));
        return bottle;
    }
}
