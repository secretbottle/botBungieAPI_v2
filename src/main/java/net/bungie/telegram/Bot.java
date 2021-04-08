package net.bungie.telegram;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.bungie.telegram.commands.BotCommandNoArgument;
import net.bungie.telegram.commands.BotCommandWithArgument;
import net.bungie.telegram.commands.CommandRegister;
import net.bungie.telegram.commands.impl.Register;
import net.bungie.telegram.commands.impl.Xur;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class Bot extends TelegramLongPollingBot {
    private final CommandRegister<BotCommandWithArgument> commandRegisterWithArg = new CommandRegister<>();
    private final CommandRegister<BotCommandNoArgument> commandRegisterNoArg = new CommandRegister<>();

    @Getter
    @Value("${api.name}")
    private String botUsername;

    @Getter
    @Value("${api.token}")
    private String botToken;

    public Bot() {
        super();

        commandRegisterWithArg.register(new Register("/register", "Command register player character"));

        commandRegisterNoArg.register(new Xur("/xur", "Command return info about vendor Xur "));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.isCommand()) {
                String[] commands = message.getText().split("\\s+");
                String command = commands[0].substring(1).toLowerCase();
                String answer = switch (command) {
                    case "xur" -> commandRegisterNoArg.getCommand(command).execute();
                    case "register" -> commandRegisterWithArg.getCommand(command).execute(commands[1]);
                    default -> "Команда не найдена";
                };
                sendAnswer(message, answer);
            }
        }
    }

    private void sendAnswer(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        //sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error while sending answer from Bot to User: ", e);
        }
    }
}
