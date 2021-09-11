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

import static com.sportdataapi.data.MatchStatus.ENDED;

public class LastMatchCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final SportClient sportClient;
    private final static int ID_TEAM = 2523;
    private final static int ID_SEASON = 1980;

    public LastMatchCommand(SendBotMessageService sendBotMessageService, SportClient sportClient) {
        this.sendBotMessageService = sendBotMessageService;
        this.sportClient = sportClient;
    }

    @Override
    public void execute(Update update) {
        List<Match> matchList = sportClient.getClient().list(ID_SEASON, ENDED).stream()
                .filter(status -> status.getStatus().equals(ENDED))
                .filter(team -> team.getHomeTeam().getId() == ID_TEAM | team.getGuestTeam().getId() == ID_TEAM)
                .collect(Collectors.toList());
        Match lastMatch = null;
        if (matchList.stream().skip(matchList.size() - 1).findAny().isPresent())
        lastMatch = matchList.stream().skip(matchList.size() - 1).findAny().get();

        if (lastMatch != null)
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                lastMatch.getHomeTeam().getName() + " - " + lastMatch.getGuestTeam().getName() +
                        "\nResult " + lastMatch.getResults().getScores().getHomeScore() + ":" +
                        lastMatch.getResults().getScores().getGuestScore() +
                        "\n" + lastMatch.getStart().getTime());
        else sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                "Sorry, match not found");

    }
}
