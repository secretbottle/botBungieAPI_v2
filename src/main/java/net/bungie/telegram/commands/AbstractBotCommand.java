package net.bungie.telegram.commands;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class AbstractBotCommand {

    private final String commandIdentifier;
    private final String description;
    private final int numberArgs;

    public AbstractBotCommand(String commandIdentifier, String description) {
        this(commandIdentifier, description, 2);
    }

    public AbstractBotCommand(String commandIdentifier, String description, int numberArgs) {
        String COMMAND_INIT_CHARACTER = "/";
        int COMMAND_MAX_LENGTH = 32;

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

}
