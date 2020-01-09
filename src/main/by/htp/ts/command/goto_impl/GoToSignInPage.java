package main.by.htp.ts.command.goto_impl;

import main.by.htp.ts.command.Command;
import main.by.htp.ts.command.concrete_impl.RequestParameter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToSignInPage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession(false).setAttribute("goto_request", RequestParameter.SIGN_IN_PAGE);
		RequestDispatcher dispatcher = request.getRequestDispatcher(RequestParameter.SIGN_IN_PAGE);
		dispatcher.forward(request, response);
	}

}
