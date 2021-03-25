package net.bungie.telegram.commands;

public abstract class BotCommandNoArgument extends AbstractBotCommand {

    public BotCommandNoArgument(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    public BotCommandNoArgument(String commandIdentifier, String description, int numberArgs) {
        super(commandIdentifier, description, numberArgs);
    }

    abstract public String execute();
}
