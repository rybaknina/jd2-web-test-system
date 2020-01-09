package main.by.htp.ts.command.goto_impl;

import main.by.htp.ts.command.Command;
import main.by.htp.ts.command.concrete_impl.RequestParameter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToRegistrationPage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		session.setAttribute("goto_request", RequestParameter.REGISTRATION_PAGE);
		if(session == null) {
			response.sendRedirect("controller?command=go_to_sign_in_page&errorMessage=session invalidate");
			return;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(RequestParameter.REGISTRATION_PAGE);
		dispatcher.forward(request, response);
		
	}

}
