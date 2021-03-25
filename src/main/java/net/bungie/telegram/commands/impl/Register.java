package net.bungie.telegram.commands.impl;

import net.bungie.telegram.commands.BotCommandWithArgument;

/**
 * Command register player character
 */
public class Register extends BotCommandWithArgument {

    public Register(String commandId, String description) {
        super(commandId, description);
    }

    @Override
    public String execute(String argument) {
        return null;
    }

}
