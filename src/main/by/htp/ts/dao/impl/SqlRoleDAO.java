package main.by.htp.ts.dao.impl;

import main.by.htp.ts.bean.UserRole;
import main.by.htp.ts.command.concrete_impl.RequestParameter;
import main.by.htp.ts.dao.RoleDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlRoleDAO implements RoleDAO {
    private static final Logger LOGGER = Logger.getLogger(SqlRoleDAO.class.getName());
    private static final String SELECT_ROLE_ID = "select role_id from role where title = ?";

    @Override
    public int findRoleId() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int role_id=0;
        try {
            connection = ConnectionPool.getConnectionPool().takeConnection();
            ps = connection.prepareStatement(SELECT_ROLE_ID);
            ps.setString(1, String.valueOf(UserRole.USER));
            rs = ps.executeQuery();
            if (rs.next()) {
                role_id = rs.getInt(rs.getString(RequestParameter.ROLE));
            }

        } catch (ConnectionPoolException e) {
            LOGGER.log(Level.SEVERE, "ConnectionPoolException occur", e);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException occur", e);
        } finally {
            ConnectionPool.getConnectionPool().closeConnection(connection,ps,rs);
        }
        return role_id;
    }
}
