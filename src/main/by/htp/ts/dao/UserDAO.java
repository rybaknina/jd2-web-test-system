package main.by.htp.ts.dao;

import main.by.htp.ts.bean.User;

public interface UserDAO {
    void registration(User user) throws DAOException;

    User authorization(String email, String password) throws DAOException;
}
