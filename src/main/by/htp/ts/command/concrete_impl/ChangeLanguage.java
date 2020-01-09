package main.by.htp.ts.command.concrete_impl;

import main.by.htp.ts.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeLanguage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String local;
		HttpSession session = request.getSession(true);
		
		local = request.getParameter("language");
		session.setAttribute("local", local);
		
		String goToRequest = (String) session.getAttribute("goto_request");
		
		if(goToRequest!= null && !goToRequest.equals("") && goToRequest.endsWith(".jsp")) {
			request.getRequestDispatcher(goToRequest).forward(request, response);
		}else {
			response.sendRedirect(goToRequest);
		}
		
		
		
		
		
	}

}
