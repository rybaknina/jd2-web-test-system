package main.by.htp.ts.dao.impl;

import main.by.htp.ts.bean.Question;
import main.by.htp.ts.bean.Test;
import main.by.htp.ts.controller.Controller;
import main.by.htp.ts.dao.DAOException;
import main.by.htp.ts.dao.TestDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlTestDAO implements TestDAO {

    private static final Logger logger = Logger.getLogger(SqlTestDAO.class.getName());
    private static final String SELECT_TESTS = "select id, name, time from test where status = ?";
    private static final String INSERT_TEST = "insert into test (name, time, status) values(?, ?, ?)";
    private static final String SELECT_QUESTIONS = "select id, name, description from question";
    private static final ReentrantLock lock = new ReentrantLock();

    @Override
    public List<Test> listTests() throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Test> tests = new ArrayList<>();
        try {
            connection = Controller.pool.takeConnection();
            ps = connection.prepareStatement(SELECT_TESTS);
            ps.setString(1, "0");
            rs = ps.executeQuery();
            while (rs.next()) {
                Test test = new Test();
                test.setId(rs.getInt("id"));
                test.setName(rs.getString("name"));
                test.setTime(rs.getTime("time"));
                tests.add(test);
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
        return tests;
    }

    @Override
    public void addTest(Test test) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        try{
            connection = Controller.pool.takeConnection();

            connection.setAutoCommit(false);
            lock.lock();

            ps = connection.prepareStatement(INSERT_TEST);
            ps.setString(1, test.getName());
            ps.setTime(2, test.getTime());
            ps.setString(3, String.valueOf(test.getStatus()));
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
    public List<Question> listQuestions(int testId) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Question> questions = new ArrayList<>();
        try {
            connection = Controller.pool.takeConnection();
            ps = connection.prepareStatement(SELECT_QUESTIONS);
          //  ps.setInt(1, test_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setName(rs.getString("name"));
                question.setDescription(rs.getString("description"));
                questions.add(question);
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
        return questions;
    }
}
