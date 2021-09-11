package com.github.EPLBot.sportapiclient;

import com.sportdataapi.client.MatchesClient;

//EPL - English Premier League
public interface SportClient {
    MatchesClient getClient();
}
