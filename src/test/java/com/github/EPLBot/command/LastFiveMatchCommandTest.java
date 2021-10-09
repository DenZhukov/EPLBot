package com.github.EPLBot.command;

import com.github.EPLBot.bot.EplTelegramBot;
import com.github.EPLBot.repository.entity.TelegramUser;
import com.github.EPLBot.service.SendBotMessageService;
import com.github.EPLBot.service.SendBotMessageServiceImpl;
import com.github.EPLBot.service.TelegramUserService;
import com.github.EPLBot.sportapiclient.SportClient;
import com.sportdataapi.data.Match;
import com.sportdataapi.data.MatchResults;
import com.sportdataapi.data.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.EPLBot.command.CommandName.LAST_5_MATCHES;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-level testing for LastFiveMatchCommand")
class LastFiveMatchCommandTest {

    @Test
    void execute() {
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        SportClient sportClient = Mockito.mock(SportClient.class);
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);

        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setActive(true);
        telegramUser.setChatId(1L);
        telegramUser.setTeamId(2523);
        Update update = new Update();

        Integer teamId = sportClient.getTeamId(telegramUserService, update);

        //ToDo
        List<Match> matchList = new ArrayList<>();
//        matchList.add(addMatchForTest("aaa", "bbb", 111, 111, 0, 1));
//        matchList.add(addMatchForTest("aaa", "bbb", 111, 111,0, 1));
//        matchList.add(addMatchForTest("aaa", "bbb", 111, 111, 0, 1));
//        matchList.add(addMatchForTest("aaa", "bbb", 111, 111,0, 1));
//        matchList.add(addMatchForTest("aaa", "bbb", 111, 111,0, 1));

        Mockito.when(telegramUserService.findByChatId(telegramUser.getChatId())).thenReturn(Optional.of(telegramUser));

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(telegramUser.getChatId());
        Mockito.when(message.getText()).thenReturn(LAST_5_MATCHES.getCommandName());
        update.setMessage(message);

        String fiveMatches = matchList.stream()
                .map(match -> match.getHomeTeam().getName() + " - " + match.getGuestTeam().getName() +
                        " " + match.getResults().getScores().getHomeScore() + ":" +
                        match.getResults().getScores().getGuestScore() + "\n")
                .collect(Collectors.joining());

        LastFiveMatchCommand lastFiveMatchCommand = new LastFiveMatchCommand(sendBotMessageService, sportClient, telegramUserService);
        lastFiveMatchCommand.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(telegramUser.getChatId().toString(), fiveMatches);
    }

    private Match addMatchForTest(String homeName, String guestName, int homeId, int guestId,  int homeScore, int guestScore) {
        Match match = new Match();
        Team homeTeam = new Team();
        homeTeam.setId(homeId);
        homeTeam.setName(homeName);
        Team guestTeam = new Team();
        guestTeam.setName(guestName);
        guestTeam.setId(guestId);

        MatchResults matchResults = new MatchResults();
        matchResults.setHomeScore(homeScore);
        matchResults.setGuestScore(guestScore);

        match.setHomeTeam(homeTeam);
        match.setGuestTeam(guestTeam);
        match.setResults(matchResults);
        return match;
    }
}