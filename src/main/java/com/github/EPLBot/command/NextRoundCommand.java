package com.github.EPLBot.command;

import com.github.EPLBot.service.SendBotMessageService;
import com.github.EPLBot.sportapiclient.SportClient;
import com.sportdataapi.data.Match;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.sportdataapi.data.MatchStatus.NOT_STARTED;

public class NextRoundCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final SportClient sportClient;

    public NextRoundCommand(SendBotMessageService sendBotMessageService, SportClient sportClient) {
        this.sendBotMessageService = sendBotMessageService;
        this.sportClient = sportClient;
    }

    @Override
    public void execute(Update update) {
        List<Match> matchesMap = sportClient.getMatchesList(sportClient.getIdSeason(), NOT_STARTED).stream()
                .filter(round -> round.getRound().isCurrent()).sorted(new Comparator<Match>() {
                    @Override
                    public int compare(Match o1, Match o2) {
                        return o1.getStart().getTime().hashCode() - o2.getStart().getTime().hashCode();
                    }
                })
                .collect(Collectors.toList());

        String roundMatches = matchesMap.stream()
                .map(match -> String.format("%s-%s %s\n", match.getHomeTeam().getName(), match.getGuestTeam().getName(),
                        sportClient.getDateFormat().format(match.getStart().getTime())))
                .collect(Collectors.joining());

         sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), roundMatches);
    }
}
