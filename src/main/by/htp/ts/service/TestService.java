package main.by.htp.ts.service;

import main.by.htp.ts.bean.Question;
import main.by.htp.ts.bean.Test;

import java.util.List;

public interface TestService {
    List<Test> listTests() throws ServiceException;
    void addTest(Test test) throws ServiceException;
    List<Question> listQuestions(int testId) throws ServiceException;
}
