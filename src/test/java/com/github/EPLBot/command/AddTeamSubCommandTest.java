package com.github.EPLBot.command;

import com.github.EPLBot.bot.EplTelegramBot;
import com.github.EPLBot.repository.entity.TelegramUser;
import com.github.EPLBot.service.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-level testing for AddTeamSubCommand")
class AddTeamSubCommandTest {

    private EplTelegramBot eplTelegramBot;
    private TelegramUserService telegramUserService;
    private TelegramUser telegramUser;
    private Integer teamId;

    @BeforeEach
    void setUp() {
        telegramUserService = Mockito.mock(TelegramUserService.class);
        eplTelegramBot = Mockito.mock(EplTelegramBot.class);

        telegramUser = new TelegramUser();
        telegramUser.setChatId(123123L);
        telegramUser.setActive(true);

        teamId = 123;
    }

    @Test
    void execute() {
        telegramUser.setTeamId(teamId);
        telegramUserService.save(telegramUser);

        Assertions.assertEquals(teamId, telegramUser.getTeamId());
    }

}