package main.by.htp.ts.command;

import main.by.htp.ts.command.concrete_impl.*;
import main.by.htp.ts.command.goto_impl.*;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {

    private Map<CommandName, Command> commands = new HashMap<CommandName, Command>();

    public CommandProvider() {
        commands.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.LOCALIZATION, new ChangeLanguage());

        commands.put(CommandName.GO_TO_SIGN_IN_PAGE, new GoToSignInPage());
        commands.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPage());
        commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPage());
        commands.put(CommandName.SIGN_OUT, new SignOut());
        commands.put(CommandName.GO_TO_TEST_PAGE, new GoToTestPage());
        commands.put(CommandName.ADD_TEST, new AddTestCommand());
        commands.put(CommandName.GO_TO_ADD_TEST_PAGE, new GoToAddTestPage());
        commands.put(CommandName.START_TEST, new StartTestCommand());
    }

    public Command getCommand(String name){
        CommandName commandName;

        commandName = CommandName.valueOf(name.toUpperCase());
        return commands.get(commandName);
    }

}
