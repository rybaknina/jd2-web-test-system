package main.by.htp.ts.command.goto_impl;

import main.by.htp.ts.bean.Test;
import main.by.htp.ts.command.Command;
import main.by.htp.ts.command.concrete_impl.RequestParameter;
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
import java.util.List;

public class GoToTestPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DAOException {
        ServiceProvider provider = ServiceProvider.getInstance();
        TestService testService = provider.getTestService();

        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendRedirect("controller?command=go_to_sign_in_page&errorMessage=session invalidate");
            return;
        }
        session.setAttribute("goto_request", RequestParameter.TEST_PAGE);
        try {
            List<Test> tests = testService.listTests();
            if (tests == null || tests.size()==0){
                response.sendRedirect("controller?command=go_to_sign_in_page&errorMessage=get list of tests error");
                return;
            }

            session.setAttribute("listTests", tests);
            RequestDispatcher dispatcher = request.getRequestDispatcher(RequestParameter.TEST_PAGE);
            dispatcher.forward(request, response);

        } catch (ServiceException e) {
            response.sendRedirect(RequestParameter.ERROR_PAGE);
        }
    }
}
