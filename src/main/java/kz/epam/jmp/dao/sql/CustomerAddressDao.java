package kz.epam.jmp.dao.sql;

import kz.epam.jmp.dao.DaoException;
import kz.epam.jmp.dao.GenericDao;
import kz.epam.jmp.entity.CustomerAddress;
import kz.epam.jmp.connection.ConnectionPool;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("addressDao")
public class CustomerAddressDao implements GenericDao<CustomerAddress> {

    private static final Logger LOGGER = Logger.getLogger(CustomerAddressDao.class);
    private ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void add(CustomerAddress address) throws DaoException {
        String sql = "INSERT INTO CUSTOMER_ADDRESS(CITY, STREET, HOUSE_NUMBER, APARTMENT_NUMBER, PHONE_NUMBER, CUSTOMER_ID) VALUES( ?, ?, ?, ?, ?, ?)";
        addUpdateAddress(address, sql);
    }

    @Override
    public void update(CustomerAddress address) throws DaoException {
        String sql = "UPDATE CUSTOMER_ADDRESS SET CITY=?, STREET=?, HOUSE_NUMBER=?, APARTMENT_NUMBER=?, PHONE_NUMBER=?, CUSTOMER_ID=? WHERE ID=?";
        addUpdateAddress(address, sql);
    }


    @Override
    public List<CustomerAddress> getAll() throws DaoException {
        Connection connection = pool.getConnection();
        List<CustomerAddress> addressList = new ArrayList<>();
        String sql = "SELECT ID, CITY, STREET, HOUSE_NUMBER, APARTMENT_NUMBER, PHONE_NUMBER, CUSTOMER_ID FROM CUSTOMER_ADDRESS";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CustomerAddress address = getCustomerAddressFieldsFromDB(resultSet);
                addressList.add(address);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get all addresses SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return addressList;
    }

    @Override
    public CustomerAddress getById(int id) throws DaoException {
        String sql = "SELECT ID, CITY, STREET, HOUSE_NUMBER, APARTMENT_NUMBER, PHONE_NUMBER, CUSTOMER_ID FROM CUSTOMER_ADDRESS WHERE ID=?";
        return getByParameter(id, sql);
    }

    public CustomerAddress getByCustomerId(int id) throws DaoException {
        String sql = "SELECT ID, CITY, STREET, HOUSE_NUMBER, APARTMENT_NUMBER, PHONE_NUMBER, CUSTOMER_ID FROM CUSTOMER_ADDRESS WHERE CUSTOMER_ID=?";
        return getByParameter(id, sql);
    }

    @Override
    public void remove(CustomerAddress entity) throws DaoException {
        throw new DaoException("It's not allowed to delete address!");
    }

    private void addUpdateAddress(CustomerAddress address, String sqlParameter) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlParameter);
            preparedStatement.setString(1, address.getCity());
            preparedStatement.setString(2, address.getStreet());
            preparedStatement.setString(3, address.getHouseNumber());
            preparedStatement.setString(4, address.getApartmentNumber());
            preparedStatement.setString(5, address.getPhoneNumber());
            preparedStatement.setInt(6, address.getCustomerId());
            if (address.getId() != null) preparedStatement.setInt(7, address.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Address creating and updating SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
    }

    private CustomerAddress getByParameter(Object parameter, String sqlParameter) throws DaoException {
        Connection connection = pool.getConnection();
        CustomerAddress address = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlParameter);
            preparedStatement.setObject(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                address = getCustomerAddressFieldsFromDB(resultSet);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get address by " + parameter + " id SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return address;
    }

    private CustomerAddress getCustomerAddressFieldsFromDB(ResultSet resultSet) throws SQLException {
        CustomerAddress address = new CustomerAddress();
        address.setId(resultSet.getInt("ID"));
        address.setCity(resultSet.getString("CITY"));
        address.setStreet(resultSet.getString("STREET"));
        address.setHouseNumber(resultSet.getString("HOUSE_NUMBER"));
        address.setApartmentNumber(resultSet.getString("APARTMENT_NUMBER"));
        address.setPhoneNumber(resultSet.getString("PHONE_NUMBER"));
        address.setCustomerId(resultSet.getInt("CUSTOMER_ID"));
        return address;
    }

}
