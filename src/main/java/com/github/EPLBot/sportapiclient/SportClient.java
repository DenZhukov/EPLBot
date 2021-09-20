package com.github.EPLBot.sportapiclient;

import com.github.EPLBot.service.TelegramUserService;
import com.sportdataapi.SdaClient;
import com.sportdataapi.client.MatchesClient;
import com.sportdataapi.data.Match;
import com.sportdataapi.data.MatchStatus;
import com.sportdataapi.data.Team;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

public interface SportClient {

    SdaClient getSdaClient();

    MatchesClient getMatchesClient();

    Integer getTeamId(TelegramUserService telegramUserService, Update update);

    List<Match> getMatchesList(int seasonId, Integer teamId, MatchStatus status);

    List<Match> getMatchesList(int seasonId, MatchStatus status);

}
