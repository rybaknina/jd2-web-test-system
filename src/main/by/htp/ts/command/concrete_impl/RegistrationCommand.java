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
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class RegistrationCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceProvider provider = ServiceProvider.getInstance();
		UserService userService = provider.getUserService();

		HttpSession session = request.getSession(true);
	//	session.setAttribute("local", request.getParameter("language"));

		String email = request.getParameter(RequestParameter.EMAIL);
		String password = request.getParameter(RequestParameter.PASSWORD);
		String name = request.getParameter(RequestParameter.NAME);
		String lastName = request.getParameter(RequestParameter.LAST_NAME);
		java.sql.Date birthday = null;
		birthday = java.sql.Date.valueOf(request.getParameter(RequestParameter.BIRTHDAY));
		int role_id = 1;
		User user = null;

		try {
			user = userService.authorization(email,password);

			if (user != null) {
				response.sendRedirect("controller?command=go_to_sign_in_page&errorMessage=user already exist");
				return;
			}

			user = new User(email, password, name, lastName, birthday, role_id);
			userService.registration(user);

			session.setAttribute("user", user);
			// go to main page
			response.sendRedirect("controller?command=go_to_test_page");

		} catch (ServiceException e) {
			LOGGER.log(Level.SEVERE, "DAOException occur", e);
			response.sendRedirect(RequestParameter.ERROR_PAGE + "?errorMessage="+e.getMessage()); // no-no
		}

		
	}

}
