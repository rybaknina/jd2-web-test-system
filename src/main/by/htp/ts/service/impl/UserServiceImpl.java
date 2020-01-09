package main.by.htp.ts.service.impl;

import com.mysql.jdbc.StringUtils;
import main.by.htp.ts.bean.User;
import main.by.htp.ts.dao.DAOException;
import main.by.htp.ts.dao.DAOFactory;
import main.by.htp.ts.dao.UserDAO;
import main.by.htp.ts.service.ServiceException;
import main.by.htp.ts.service.UserService;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    @Override
    public void registration(User user) throws ServiceException {

        if (StringUtils.isEmptyOrWhitespaceOnly(user.getEmail()) || StringUtils.isEmptyOrWhitespaceOnly(user.getPassword())){
            throw new ServiceException("Email or password incorrect");
        }
        if (!isValidEmailAddress(user.getEmail())) {
            throw new ServiceException("Email incorrect");
        }
        try {
            this.userDAO.registration(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User authorization(String email, String password) throws ServiceException {
        if (StringUtils.isEmptyOrWhitespaceOnly(email) || StringUtils.isEmptyOrWhitespaceOnly(password)){
            return null;
        }
        if (!isValidEmailAddress(email)) {
            return null;
        }
        try {
            return this.userDAO.authorization(email, password);
        } catch (DAOException e) {
            throw new ServiceException(e);   // horror
        }
    }
    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
