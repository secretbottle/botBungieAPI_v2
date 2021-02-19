package net.bungie.telegram.commands;

import lombok.Getter;
import lombok.ToString;

@ToString
public abstract class AbstractBotCommandWIthArgument {
    private static final String COMMAND_INIT_CHARACTER = "/";
    private static final int COMMAND_MAX_LENGTH = 32;

    @Getter
    private final String commandIdentifier;
    @Getter
    private final String description;
    @Getter
    private final int numberArgs;

    public AbstractBotCommandWIthArgument(String commandIdentifier, String description) {
        this(commandIdentifier, description, 2);
    }

    public AbstractBotCommandWIthArgument(String commandIdentifier, String description, int numberArgs) {
        if (commandIdentifier == null || commandIdentifier.isEmpty()) {
            throw new IllegalArgumentException("commandId for command cannot be null or empty");
        }

        if (commandIdentifier.startsWith(COMMAND_INIT_CHARACTER)) {
            commandIdentifier = commandIdentifier.substring(1);
        }

        if (commandIdentifier.length() + 1 > COMMAND_MAX_LENGTH) {
            throw new IllegalArgumentException("commandIdentifier cannot be longer than " + COMMAND_MAX_LENGTH + " (including " + COMMAND_INIT_CHARACTER + ")");
        }

        this.commandIdentifier = commandIdentifier.toLowerCase();
        this.description = description;
        this.numberArgs = numberArgs;
    }

    abstract public void execute(String[] arguments);
}
