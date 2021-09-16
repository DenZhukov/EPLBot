package com.github.EPLBot.service;

import com.github.EPLBot.bot.EplTelegramBot;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final EplTelegramBot eplTelegramBot;

    @Autowired
    public SendBotMessageServiceImpl(EplTelegramBot eplTelegramBot) {
        this.eplTelegramBot = eplTelegramBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            eplTelegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
