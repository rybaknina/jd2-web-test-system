package main.by.htp.ts.service.impl;

import main.by.htp.ts.dao.DAOException;
import main.by.htp.ts.dao.DAOFactory;
import main.by.htp.ts.dao.RoleDAO;
import main.by.htp.ts.service.RoleService;

public class RoleServiceImpl implements RoleService {

    private RoleDAO roleDAO = DAOFactory.getInstance().getRoleDAO();

    @Override
    public int findRoleId() throws DAOException {
        return roleDAO.findRoleId();
    }
}
