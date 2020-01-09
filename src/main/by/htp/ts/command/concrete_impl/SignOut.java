package main.by.htp.ts.command.concrete_impl;

import main.by.htp.ts.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignOut implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =  request.getSession(false);
		
		if(session != null) {
			session.removeAttribute("user");
			session.setAttribute("goto_request", "controller?command=go_to_sign_in_page");

		}
		
		response.sendRedirect("controller?command=go_to_sign_in_page");
		
	}

}
