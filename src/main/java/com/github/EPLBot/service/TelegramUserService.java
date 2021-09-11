package com.github.EPLBot.service;

import com.github.EPLBot.repository.entity.TelegramUser;

import java.util.List;
import java.util.Optional;


public interface TelegramUserService {

    void save(TelegramUser telegramUser);

    List<TelegramUser> findAllActiveUsers();

    Optional<TelegramUser> findByChatId(Integer chatId); //todo разобраться с Integer and Long/String
}