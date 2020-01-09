package main.by.htp.ts.service.impl;

import main.by.htp.ts.bean.Question;
import main.by.htp.ts.bean.Test;
import main.by.htp.ts.dao.DAOException;
import main.by.htp.ts.dao.DAOFactory;
import main.by.htp.ts.dao.TestDAO;
import main.by.htp.ts.service.ServiceException;
import main.by.htp.ts.service.TestService;

import java.util.List;

public class TestServiceImpl implements TestService {
    private TestDAO testDAO = DAOFactory.getInstance().getTestDAO();
    @Override
    public List<Test> listTests() throws ServiceException {
        try {
            return this.testDAO.listTests();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addTest(Test test) throws ServiceException {
        try {
            this.testDAO.addTest(test);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Question> listQuestions(int test_id) throws ServiceException {
        try {
            return this.testDAO.listQuestions(test_id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
