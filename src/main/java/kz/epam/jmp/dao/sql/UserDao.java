package kz.epam.jmp.dao.sql;

import kz.epam.jmp.dao.DaoException;
import kz.epam.jmp.dao.GenericDao;
import kz.epam.jmp.entity.User;
import kz.epam.jmp.connection.ConnectionPool;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("userDao")
public class UserDao implements GenericDao<User> {
    private static final Logger LOGGER = Logger.getLogger(UserDao.class);
    private ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void add(User user) throws DaoException {
        String sql = "INSERT INTO USER(FIRSTNAME, LASTNAME, LOGINEMAIL, PASSWORD, ROLE, WALLET, STATE) VALUES( ?, ?, ?, ?, ?, ?, ?)";
        addUpdateUser(user, sql);
    }

    @Override
    public void update(User user) throws DaoException {
        String sql = "UPDATE USER SET FIRSTNAME=?, LASTNAME=?, LOGINEMAIL=?, PASSWORD=?, ROLE=?, WALLET=?, STATE=? WHERE ID=?";
        addUpdateUser(user, sql);
    }

    @Override
    public User getById(int id) throws DaoException {
        return getByParameter(id, "ID");
    }

    public User getByLogin(String loginEmail) throws DaoException {
        return getByParameter(loginEmail, "LOGINEMAIL");
    }

    @Override
    public List<User> getAll() throws DaoException {
        Connection connection = pool.getConnection();
        User user;
        List<User> userList = new ArrayList<>();
        String sql = "SELECT ID, FIRSTNAME, LASTNAME, LOGINEMAIL, PASSWORD, ROLE, WALLET, STATE FROM USER";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = getUserFieldsFromDB(resultSet);
                userList.add(user);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get all users SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return userList;
    }

    private void addUpdateUser(User user, String sqlParameter) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlParameter);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLoginEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, String.valueOf(user.getRole()));
            preparedStatement.setDouble(6, user.getWallet());
            preparedStatement.setString(7, String.valueOf(user.getState()));
            if (user.getId() != null) preparedStatement.setInt(8, user.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("User adding or updating SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
    }

    private User getByParameter(Object parameter, String sqlParameter) throws DaoException {
        Connection connection = pool.getConnection();
        User user = null;
        String sql = "SELECT ID, FIRSTNAME, LASTNAME, LOGINEMAIL, PASSWORD, ROLE, WALLET, STATE FROM USER WHERE " + sqlParameter + "=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = getUserFieldsFromDB(resultSet);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Get user by " + parameter + " SQLException", e);
            throw new DaoException();
        } finally {
            if (connection != null) {
                pool.returnConnection(connection);
            }
        }
        return user;
    }

    private User getUserFieldsFromDB(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("ID"));
        user.setFirstName(resultSet.getString("FIRSTNAME"));
        user.setLastName(resultSet.getString("LASTNAME"));
        user.setLoginEmail(resultSet.getString("LOGINEMAIL"));
        user.setPassword(resultSet.getString("PASSWORD"));
        user.setRole(User.Role.valueOf(resultSet.getString("ROLE")));
        user.setWallet(resultSet.getDouble("WALLET"));
        user.setState(User.State.valueOf(resultSet.getString("STATE")));
        return user;
    }

    @Override
    public void remove(User user) throws DaoException {
        throw new DaoException("It's not allowed to delete user!");
    }
}


