package net.bungie.telegram.commands;

/**
 * Command return info about Xur vendor
 */
public class Xur extends AbstractBotCommandWIthArgument {

    public Xur(String commandId, String description) {
        super(commandId, description, 1);
    }

    @Override
    public void execute(String[] arguments) {
        //TODO Work here
    }
}
