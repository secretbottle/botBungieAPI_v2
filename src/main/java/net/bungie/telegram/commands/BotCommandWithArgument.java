package net.bungie.telegram.commands;

public abstract class BotCommandWithArgument extends AbstractBotCommand {

    public BotCommandWithArgument(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    public BotCommandWithArgument(String commandIdentifier, String description, int numberArgs) {
        super(commandIdentifier, description, numberArgs);
    }

    abstract public String execute(String argument);
}
