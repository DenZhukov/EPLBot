package com.github.EPLBot.command;

import com.github.EPLBot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknownCommand implements Command{
    private final SendBotMessageService sendBotMessageService;

    public final static String UNKNOWN_MESSAGE = "I don't know what you want, just use command /help. " +
            "It was made for people like you.";

    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE);
    }
}
