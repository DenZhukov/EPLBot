package com.github.EPLBot.command;

import com.github.EPLBot.service.SendBotMessageService;
import com.sportdataapi.SdaClient;
import com.sportdataapi.SdaClientFactory;
import com.sportdataapi.client.LeaguesClient;
import com.sportdataapi.client.TeamsClient;
import com.sportdataapi.data.League;
import com.sportdataapi.data.Team;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class TestCommand implements Command{
    private final SendBotMessageService sendBotMessageService;

    public TestCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

//    @Override
//    public void execute(Update update) {
//
//        JfdataManager jfdataManager = new JfdataManager("${football.api}");
//        TeamList actual = jfdataManager.getTeamsByCompetition(2015);
//
//        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), actual.toString());
//    }


    @Override
    public void execute(Update update) {
        SdaClient client = SdaClientFactory.newClient(" ");
        LeaguesClient leaguesClient = client.soccer().leagues();
        League EPL = leaguesClient.get(237);
        TeamsClient teamsClient = client.soccer().teams();
        List<Team> EplTeams = teamsClient.list(EPL.getCountryId());
        for (int i = 0; i <20; i++) {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), EplTeams.get(i).getName());
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.valueOf(EplTeams.get(i).getId()));
        }

    }
}
