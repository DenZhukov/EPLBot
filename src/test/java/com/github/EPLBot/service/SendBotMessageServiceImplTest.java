package com.github.EPLBot.service;

import com.github.EPLBot.bot.EplTelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit level testing for SendBotMessageService")
class SendBotMessageServiceImplTest {

    private SendBotMessageService sendBotMessageService;
    private EplTelegramBot eplTelegramBot;

    @BeforeEach
    void setUp() {
        eplTelegramBot = Mockito.mock(EplTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(eplTelegramBot);
    }

    @Test
    void sendMessage() throws TelegramApiException {
        String chatId = "123";
        String text = "Text";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.enableHtml(true);

        sendBotMessageService.sendMessage(chatId, text);

        Mockito.verify(eplTelegramBot).execute(sendMessage);
    }
}