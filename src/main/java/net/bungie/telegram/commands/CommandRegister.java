package net.bungie.telegram.commands;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class CommandRegister<T extends AbstractBotCommand> {
    private final Map<String, T> commandsMap = new HashMap<>();

    public final T register(T command) {
        return commandsMap.putIfAbsent(command.getCommandIdentifier(), command);
    }

    public final T deregister(String commandName) {
        return commandsMap.remove(commandName);
    }

    public final T getCommand(String commandName) {
        return commandsMap.get(commandLowerCaseWithoutSlash(commandName));
    }

    public final void deregisterAll() {
        commandsMap.clear();
    }

    public final boolean isRegister(String commandName) {
        return commandsMap.containsKey(commandLowerCaseWithoutSlash(commandName));
    }

    private String commandLowerCaseWithoutSlash(String commandName) {
        commandName = commandName.toLowerCase();
        if (commandName.startsWith("/")) {
            return commandName.substring(1);
        }
        return commandName;
    }
}
