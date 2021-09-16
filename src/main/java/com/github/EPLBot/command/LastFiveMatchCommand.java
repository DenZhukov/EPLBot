package com.github.EPLBot.command;

import com.github.EPLBot.service.SendBotMessageService;
import com.github.EPLBot.service.TelegramUserService;
import com.github.EPLBot.sportapiclient.SportClient;
import com.sportdataapi.data.Match;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

import static com.sportdataapi.data.MatchStatus.ENDED;

public class LastFiveMatchCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final SportClient sportClient;
    private final TelegramUserService telegramUserService;
    private final static int ID_SEASON = 1980;

    public LastFiveMatchCommand(SendBotMessageService sendBotMessageService, SportClient sportClient, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.sportClient = sportClient;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        Integer teamId = sportClient.getTeamId(telegramUserService, update);
        List<Match> matchList = sportClient.getMatchesList(ID_SEASON, teamId, ENDED).stream()
                .limit(5).collect(Collectors.toList());

        String fiveMatches = matchList.stream()
                .map(match -> match.getHomeTeam().getName() + " - " + match.getGuestTeam().getName() +
                        " " + match.getResults().getScores().getHomeScore() + ":" +
                        match.getResults().getScores().getGuestScore() + "\n")
                .collect(Collectors.joining());

        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), fiveMatches);
    }
}