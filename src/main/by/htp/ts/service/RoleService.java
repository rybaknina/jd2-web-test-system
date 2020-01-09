package main.by.htp.ts.service;

import main.by.htp.ts.dao.DAOException;

public interface RoleService {
    int findRoleId() throws DAOException;
}
