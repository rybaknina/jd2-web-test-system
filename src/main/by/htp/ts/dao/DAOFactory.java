package main.by.htp.ts.dao;

import main.by.htp.ts.dao.impl.SqlRoleDAO;
import main.by.htp.ts.dao.impl.SqlTestDAO;
import main.by.htp.ts.dao.impl.SqlUserDAO;

public class DAOFactory {
    private static final DAOFactory INSTANCE = new DAOFactory();
    private final UserDAO sqlUserDAO = new SqlUserDAO();
    private final RoleDAO sqlRoleDAO = new SqlRoleDAO();
    private final TestDAO sqlTestDAO = new SqlTestDAO();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return INSTANCE;
    }
    public UserDAO getUserDAO() {
        return sqlUserDAO;
    }
    public RoleDAO getRoleDAO(){
        return sqlRoleDAO;
    }
    public TestDAO getTestDAO() {return sqlTestDAO; }
}
