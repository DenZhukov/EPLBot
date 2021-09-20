package com.github.EPLBot.command;

import com.github.EPLBot.service.SendBotMessageService;
import com.github.EPLBot.sportapiclient.SportClient;
import com.sportdataapi.data.Match;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.sportdataapi.data.MatchStatus.ENDED;

public class LastRoundCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final SportClient sportClient;
    private final static int ID_SEASON = 1980;

    public LastRoundCommand(SendBotMessageService sendBotMessageService, SportClient sportClient) {
        this.sendBotMessageService = sendBotMessageService;
        this.sportClient = sportClient;
    }

    @Override
    public void execute(Update update) {
        //toDo refactor; use Round (may be miss when some team skips a match)
        List<Match> matchesMap = sportClient.getMatchesList(ID_SEASON, ENDED).stream()
                .sorted(new Comparator<Match>() {
                    @Override
                    public int compare(Match o1, Match o2) {
                        return o1.getStart().getTime().hashCode() - o2.getStart().getTime().hashCode();
                    }
                })
                .collect(Collectors.toList());

        String roundMatches = matchesMap.stream()
                .map(match -> String.format("%s %s %d:%d\n", match.getHomeTeam().getName(), match.getGuestTeam().getName(),
                        match.getResults().getScores().getHomeScore(), match.getResults().getScores().getGuestScore()))
                .collect(Collectors.joining());

        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), roundMatches);
    }
}
