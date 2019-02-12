package kz.epam.jmp.dao.sql;

import kz.epam.jmp.dao.DaoException;
import kz.epam.jmp.dao.GenericDao;
import kz.epam.jmp.entity.Water;
import kz.epam.jmp.connection.ConnectionPool;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("waterDao")
public class WaterDao implements GenericDao<Water> {

    private static final Logger LOGGER = Logger.getLogger(WaterDao.class);
    private ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void add(Water water) throws DaoException {
        String sqlParameter = "INSERT INTO WATER(TYPE, PRICE_PER_LITER) VALUES(?, ?)";
        addUpdateWater(water, sqlParameter);
    }

    @Override
    public void update(Water water) throws DaoException {
        String sqlParameter = "UPDATE WATER SET TYPE=?, PRICE_PER_LITER=? WHERE ID=?";
        addUpdateWater(water, sqlParameter);
    }

    @Override
    public Water getById(int id) throws DaoException {
        return getByParameter(id, "ID");
    }

    public Water getByType(String type) throws DaoException {
        return getByParameter(type, "TYPE");
    }

    @Override
    public List<Water> getAll() throws DaoException {
        Connection connection = pool.getConnection();
        List<Water> waterList = new ArrayList<>();
        String sql = "SELECT ID, TYPE, PRICE_PER_LITER FROM WATER";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Water water = getWaterFieldsFromDB(resultSet);
                waterList.add(water);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get all water SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return waterList;
    }

    @Override
    public void remove(Water water) throws DaoException {
        throw new DaoException("It's not allowed to delete water!");
    }

    private void addUpdateWater(Water water, String sqlParameter) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlParameter);
            preparedStatement.setString(1, water.getType());
            preparedStatement.setInt(2, water.getPricePerLiter());
            if (water.getId() != null) preparedStatement.setInt(3, water.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("User creating or updating SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
    }

    private Water getByParameter(Object parameter, String sqlParameter) throws DaoException {
        Connection connection = pool.getConnection();
        Water water = null;
        String sql = "SELECT ID, TYPE, PRICE_PER_LITER FROM WATER WHERE " + sqlParameter + "=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                water = getWaterFieldsFromDB(resultSet);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get water by " + parameter + " SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return water;
    }

    private Water getWaterFieldsFromDB(ResultSet resultSet) throws SQLException {
        Water water = new Water();
        water.setId(resultSet.getInt("ID"));
        water.setType(resultSet.getString("TYPE"));
        water.setPricePerLiter(resultSet.getInt("PRICE_PER_LITER"));
        return water;
    }
}
