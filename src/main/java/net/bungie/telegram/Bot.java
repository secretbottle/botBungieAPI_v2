package net.bungie.telegram;

import lombok.Getter;
import net.bungie.telegram.commands.CommandRegister;
import net.bungie.telegram.commands.Register;
import net.bungie.telegram.commands.Xur;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Getter
public class Bot extends TelegramLongPollingBot{
    private final CommandRegister commandRegister = new CommandRegister();

    private final String botUsername;
    private final String botToken;

    public Bot(String botUsername, String botToken) {
        super();
        this.botUsername = botUsername;
        this.botToken = botToken;

        commandRegister.register(new Register("Register", "Command register player character"));
        commandRegister.register(new Xur("Xur", "Command return info about vendor Xur "));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.isCommand()) {
                String[] commands = message.getText().split("\\s+");
                if(commandRegister.isRegister(commands[0].toLowerCase().substring(1))){
                    //TODO work here
                };
            }
        }
    }

}
