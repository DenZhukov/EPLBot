package com.github.EPLBot.sportapiclient;

import com.github.EPLBot.service.TelegramUserService;
import com.sportdataapi.SdaClient;
import com.sportdataapi.SdaClientFactory;
import com.sportdataapi.client.MatchesClient;
import com.sportdataapi.data.Match;
import com.sportdataapi.data.MatchStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class SportMatchesClientImpl implements SportClient {
    private final String sportApiToken;
    private final static int ID_SEASON = 1980;
    private final static int ID_COUNTRY = 42;

    public SportMatchesClientImpl(@Value("${sportdataapi.token}") String sportApiToken) {
        this.sportApiToken = sportApiToken;
    }

    @Override
    public SdaClient getSdaClient() { return SdaClientFactory.newClient(sportApiToken); }

    @Override
    public MatchesClient getMatchesClient() { return getSdaClient().soccer().matches(); }

    @Override
    public Integer getTeamId(TelegramUserService telegramUserService, Update update) {
        return telegramUserService.findByChatId(update.getMessage().getChatId()).get().getTeamId();
    }

    @Override
    public List<Match> getMatchesList(int seasonId, Integer teamId, MatchStatus status){
        return getMatchesClient().list(seasonId, status).stream()
                .filter(statusMatch -> statusMatch.getStatus().equals(status))
                .filter(team -> team.getHomeTeam().getId() == teamId | team.getGuestTeam().getId() == teamId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Match> getMatchesList(int seasonId, MatchStatus status){
        return getMatchesClient().list(seasonId, status).stream()
                .filter(statusMatch -> statusMatch.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public int getIdSeason(){
        return ID_SEASON;
    }

    @Override
    public int getIdCountry(){
        return ID_COUNTRY;
    }

    @Override
    public SimpleDateFormat getDateFormat(){
        return new SimpleDateFormat("E dd-MMM HH:mm", Locale.ENGLISH);
    }
}
