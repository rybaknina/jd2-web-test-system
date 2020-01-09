package main.by.htp.ts.controller;

import main.by.htp.ts.command.Command;
import main.by.htp.ts.command.CommandProvider;
import main.by.htp.ts.dao.DAOException;
import main.by.htp.ts.dao.impl.ConnectionPool;
import main.by.htp.ts.dao.impl.ConnectionPoolException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private static final long serialVersionUID = 1L;
    private static final String COMMAND_NAME = "command";
    private final CommandProvider provider = new CommandProvider();
    public static ConnectionPool pool = ConnectionPool.getConnectionPool();
    public Controller() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            pool.initPoolData();
        } catch (ConnectionPoolException e) {
            LOGGER.log(Level.SEVERE,"Error init the connection.", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Command command;

        String commandName = request.getParameter(COMMAND_NAME);
        command = provider.getCommand(commandName);

        try {
            command.execute(request, response);
        } catch (DAOException e) {
            LOGGER.log(Level.SEVERE, "DAOException occur", e);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        pool.dispose();
    }
}
