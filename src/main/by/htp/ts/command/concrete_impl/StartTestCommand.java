package main.by.htp.ts.command.concrete_impl;

import main.by.htp.ts.bean.Question;
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
import java.util.List;

public class StartTestCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DAOException {
        ServiceProvider provider = ServiceProvider.getInstance();
        TestService testService = provider.getTestService();

        HttpSession session = request.getSession(false);
        session.setAttribute("goto_request", RequestParameter.START_TEST);

        Question question = null;
        try {
            List<Question> questions = testService.listQuestions(1);
            if (questions == null || questions.size()==0){
                response.sendRedirect("controller?command=go_to_test_page&errorMessage=get list of questions error");
                return;
            }

            session.setAttribute("listQuestions", questions);
            RequestDispatcher dispatcher = request.getRequestDispatcher(RequestParameter.TEST_PAGE);
            dispatcher.forward(request, response);

        } catch (ServiceException e) {
            response.sendRedirect(RequestParameter.ERROR_PAGE);
        }
    }
}
