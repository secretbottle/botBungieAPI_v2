package net.bungie;

import net.bungie.telegram.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class BotBungieAPIApp {
    private static Logger log = LoggerFactory.getLogger(BotBungieAPIApp.class);

    @Value("${api.name}")
    private static String BOT_NAME;

    @Value("${api.token}")
    private static String BOT_TOKEN;

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot(BOT_NAME, BOT_TOKEN));
        } catch (TelegramApiException e) {
            log.error("Bot error: ", e);
        }
    }

}
