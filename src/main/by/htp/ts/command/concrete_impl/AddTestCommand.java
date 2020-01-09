package main.by.htp.ts.command.concrete_impl;

import main.by.htp.ts.bean.Test;
import main.by.htp.ts.command.Command;
import main.by.htp.ts.dao.DAOException;
import main.by.htp.ts.service.ServiceException;
import main.by.htp.ts.service.ServiceProvider;
import main.by.htp.ts.service.TestService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Time;
import java.util.List;

public class AddTestCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider provider = ServiceProvider.getInstance();
        TestService testService = provider.getTestService();

        HttpSession session = request.getSession(false);
        session.setAttribute("goto_request", RequestParameter.ADD_TEST);

        String name = request.getParameter(RequestParameter.NAME);
        Time time = Time.valueOf(request.getParameter(RequestParameter.TIME));
        String status = request.getParameter(RequestParameter.STATUS);

        Test test = null;
        try {
            test = new Test(name,time,status);
            if (test == null){
                response.sendRedirect("controller?command=add_test&errorMessage=add test error");
                return;
            }
            testService.addTest(test);
            List<Test> tests = testService.listTests();
         //   tests.add(test);
            session.setAttribute("listTests", tests);
            RequestDispatcher dispatcher = request.getRequestDispatcher(RequestParameter.TEST_PAGE);
            dispatcher.forward(request, response);

        } catch (ServiceException e) {
            response.sendRedirect(RequestParameter.ERROR_PAGE);
        }
    }
}
