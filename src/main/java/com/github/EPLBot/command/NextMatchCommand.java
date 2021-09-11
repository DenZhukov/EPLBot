package com.github.EPLBot.command;

import com.github.EPLBot.service.SendBotMessageService;
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
    private final static int ID_TEAM = 2523;
    private final static int ID_SEASON = 1980;

    public NextMatchCommand(SendBotMessageService sendBotMessageService, SportClient sportClient) {
        this.sendBotMessageService = sendBotMessageService;
        this.sportClient = sportClient;
    }

    @Override
    public void execute(Update update) {
        List<Match> matchList = sportClient.getClient().list(ID_SEASON, NOT_STARTED).stream()
                .filter(status -> status.getStatus().equals(NOT_STARTED))
                .filter(team -> team.getHomeTeam().getId() == ID_TEAM | team.getGuestTeam().getId() == ID_TEAM)
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
