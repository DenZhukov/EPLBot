package com.github.EPLBot.sportapiclient;

import com.sportdataapi.SdaClientFactory;
import com.sportdataapi.client.MatchesClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
}
