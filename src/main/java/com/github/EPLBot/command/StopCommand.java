package com.github.EPLBot.command;

import com.github.EPLBot.service.SendBotMessageService;
import com.github.EPLBot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public final static String STOP_MESSAGE = "Ok, you should be Arsenal's fan...";

    public StopCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        telegramUserService.findByChatId(update.getMessage().getChatId())
                .ifPresent(it -> {
                    it.setActive(false);
                    telegramUserService.save(it);
                });
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), STOP_MESSAGE);
    }
}
