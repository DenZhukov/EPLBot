package com.github.EPLBot.command;

import com.github.EPLBot.service.SendBotMessageService;
import com.github.EPLBot.service.TelegramUserService;
import com.github.EPLBot.sportapiclient.SportClient;
import com.sportdataapi.data.Match;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.sportdataapi.data.MatchStatus.NOT_STARTED;

public class NextMatchCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final SportClient sportClient;
    private final TelegramUserService telegramUserService;

    public NextMatchCommand(SendBotMessageService sendBotMessageService, SportClient sportClient, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.sportClient = sportClient;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        Integer teamId = sportClient.getTeamId(telegramUserService, update);
        List<Match> matchList = sportClient.getMatchesList(sportClient.getIdSeason(), teamId, NOT_STARTED);
        Match nextMatch = null;
        if(matchList.stream().findFirst().isPresent())
            nextMatch = matchList.stream().findFirst().get();

        if(nextMatch != null)
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                nextMatch.getHomeTeam().getName() + " - " + nextMatch.getGuestTeam().getName() +
                "\n" + sportClient.getDateFormat().format(nextMatch.getStart().getTime()));
        else sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                "Sorry, match not found");

    }
}
