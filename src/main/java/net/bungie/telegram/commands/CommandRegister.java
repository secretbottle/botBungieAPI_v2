package net.bungie.telegram.commands;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class CommandRegister {
    private final Map<String, AbstractBotCommandWIthArgument> commandsMap = new HashMap<>();

    public final AbstractBotCommandWIthArgument register(AbstractBotCommandWIthArgument command) {
        return commandsMap.putIfAbsent(command.getCommandIdentifier(), command);
    }

    public final AbstractBotCommandWIthArgument deregister(String commandName) {
        return commandsMap.remove(commandName);
    }

    public final void deregisterAll() {
        commandsMap.clear();
    }

    public final boolean isRegister(String commandName) {
        return commandsMap.containsKey(commandName);
    }
}
