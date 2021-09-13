package com.github.EPLBot.command;

import com.github.EPLBot.service.SendBotMessageService;
import com.github.EPLBot.service.TelegramUserService;
import com.github.EPLBot.sportapiclient.SportClient;
import com.sportdataapi.SdaClient;
import com.sportdataapi.SdaClientFactory;
import com.sportdataapi.client.MatchesClient;
import com.sportdataapi.data.Match;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

import static com.sportdataapi.data.MatchStatus.NOT_STARTED;

public class NextMatchCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final SportClient sportClient;
    private final TelegramUserService telegramUserService;
    private final static int ID_SEASON = 1980;

    public NextMatchCommand(SendBotMessageService sendBotMessageService, SportClient sportClient, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.sportClient = sportClient;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        Integer teamId = telegramUserService.findByChatId(update.getMessage().getChatId()).get().getTeamId();
        List<Match> matchList = sportClient.getClient().list(ID_SEASON, NOT_STARTED).stream()
                .filter(status -> status.getStatus().equals(NOT_STARTED))
                .filter(team -> team.getHomeTeam().getId() == teamId | team.getGuestTeam().getId() == teamId)
                .collect(Collectors.toList());
        Match nextMatch = null;
        if(matchList.stream().findFirst().isPresent())
            nextMatch = matchList.stream().findFirst().get();

        if(nextMatch != null)
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                nextMatch.getHomeTeam().getName() + " - " + nextMatch.getGuestTeam().getName() +
                "\n" + nextMatch.getStart().getTime());
        else sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                "Sorry, match not found");

    }
}
