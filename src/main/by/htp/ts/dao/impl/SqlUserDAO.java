package main.by.htp.ts.dao.impl;

import main.by.htp.ts.bean.User;
import main.by.htp.ts.command.concrete_impl.RequestParameter;
import main.by.htp.ts.controller.Controller;
import main.by.htp.ts.dao.DAOException;
import main.by.htp.ts.dao.UserDAO;

import java.sql.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlUserDAO implements UserDAO {
    private static final Logger logger = Logger.getLogger(SqlUserDAO.class.getName());
    private static ReentrantLock lock = new ReentrantLock();

    private static final String INSERT_USER_SQL = "INSERT INTO user" + "  (email, password, name, lastname,birthday, role_id) VALUES " +
            " (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_EMAIL = "select email, password from user where email =? and password=?";


    @Override
    public void registration(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        try{
            connection = Controller.pool.takeConnection();

            connection.setAutoCommit(false);
            lock.lock();

            ps = connection.prepareStatement(INSERT_USER_SQL);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getLastname());
            ps.setObject(5, user.getBirthday());
            ps.setInt(6, 1);
            ps.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException occur", e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }

        } catch (ConnectionPoolException e) {
            logger.log(Level.SEVERE, "ConnectionPoolException occur", e);
            throw new DAOException(e);
        }
        finally {
            lock.unlock();
            Controller.pool.closeConnection(connection,ps);
        }

    }

    @Override
    public User authorization(String email, String password) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
                connection = Controller.pool.takeConnection();

                ps = connection.prepareStatement(SELECT_USER_BY_EMAIL);
                ps.setString(1, email);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if (rs.next()) {
                   if (!rs.getString(RequestParameter.PASSWORD).equals(password)){
//                      //  return null;  // bad
                        throw new DAOException("Wrong password");
                    }
                    user = new User(email, password);
                }

        } catch (ConnectionPoolException e) {
            logger.log(Level.SEVERE, "ConnectionPoolException occur", e);
            throw new DAOException(e);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException occur", e);
            throw new DAOException(e);
        } finally {
            Controller.pool.closeConnection(connection,ps,rs);
        }
        return user;
    }
}
