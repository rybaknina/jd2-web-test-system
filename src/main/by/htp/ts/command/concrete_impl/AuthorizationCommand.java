package main.by.htp.ts.command.concrete_impl;

import main.by.htp.ts.bean.User;
import main.by.htp.ts.command.Command;
import main.by.htp.ts.service.ServiceException;
import main.by.htp.ts.service.ServiceProvider;
import main.by.htp.ts.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String email = request.getParameter(RequestParameter.EMAIL);
		String password = request.getParameter(RequestParameter.PASSWORD);

		ServiceProvider provider = ServiceProvider.getInstance();
		UserService userService = provider.getUserService();
		
		User user;
		HttpSession session;
		session = request.getSession(true);

		try {
			user = userService.authorization(email, password);
			if(user == null) {
				response.sendRedirect("controller?command=go_to_sign_in_page&errorMessage=login or password error");
				return;
			}

			session.setAttribute("user", user);
			
			response.sendRedirect("controller?command=go_to_test_page");
		} catch (ServiceException e) {
			// logger
			response.sendRedirect(RequestParameter.ERROR_PAGE + "?errorMessage="+e.getMessage()); //nu-nu-nu
		}
		
	}

}
