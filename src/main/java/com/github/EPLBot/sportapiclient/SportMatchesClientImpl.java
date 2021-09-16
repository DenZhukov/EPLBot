package com.github.EPLBot.sportapiclient;

import com.github.EPLBot.service.TelegramUserService;
import com.sportdataapi.SdaClientFactory;
import com.sportdataapi.client.MatchesClient;
import com.sportdataapi.data.Match;
import com.sportdataapi.data.MatchStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SportMatchesClientImpl implements SportClient {
    private final String sportApiToken;

    public SportMatchesClientImpl(@Value("${sportdataapi.token}") String sportApiToken) {
        this.sportApiToken = sportApiToken;
    }

    @Override
    public MatchesClient getClient() {
        return SdaClientFactory.newClient(sportApiToken).soccer().matches();
    }

    @Override
    public Integer getTeamId(TelegramUserService telegramUserService, Update update) {
        return telegramUserService.findByChatId(update.getMessage().getChatId()).get().getTeamId();
    }

    @Override
    public List<Match> getMatchesList(int seasonId, Integer teamId, MatchStatus status){
        return getClient().list(seasonId, status).stream()
                .filter(statusMatch -> statusMatch.getStatus().equals(status))
                .filter(team -> team.getHomeTeam().getId() == teamId | team.getGuestTeam().getId() == teamId)
                .collect(Collectors.toList());
    }
}
