package main.by.htp.ts.service;

import main.by.htp.ts.bean.User;

public interface UserService {
    void registration(User user) throws ServiceException;
    User authorization(String email, String password) throws ServiceException;
}
