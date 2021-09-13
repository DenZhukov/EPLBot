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

import static com.sportdataapi.data.MatchStatus.ENDED;

public class LastMatchCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final SportClient sportClient;
    private final static int ID_SEASON = 1980;

    public LastMatchCommand(SendBotMessageService sendBotMessageService, SportClient sportClient, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.sportClient = sportClient;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        Integer teamId = telegramUserService.findByChatId(update.getMessage().getChatId()).get().getTeamId();
        List<Match> matchList = sportClient.getClient().list(ID_SEASON, ENDED).stream()
                .filter(status -> status.getStatus().equals(ENDED))
                .filter(team -> team.getHomeTeam().getId() == teamId | team.getGuestTeam().getId() == teamId)
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
