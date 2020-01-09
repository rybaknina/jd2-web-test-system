package main.by.htp.ts.dao;

import main.by.htp.ts.bean.Question;
import main.by.htp.ts.bean.Test;

import java.sql.Timestamp;
import java.util.List;

public interface TestDAO {
    List<Test> listTests() throws DAOException;
    void addTest(Test test) throws DAOException;
    List<Question> listQuestions(int test_id) throws DAOException;
}
