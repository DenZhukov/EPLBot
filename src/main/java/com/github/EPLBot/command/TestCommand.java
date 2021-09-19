package com.github.EPLBot.command;

import com.sportdataapi.client.MatchesClient;
import com.sportdataapi.data.*;
import com.github.EPLBot.service.SendBotMessageService;
import com.sportdataapi.SdaClient;
import com.sportdataapi.SdaClientFactory;
import com.sportdataapi.client.LeaguesClient;
import com.sportdataapi.client.MatchesClient;
import com.sportdataapi.client.SeasonsClient;
import com.sportdataapi.client.TeamsClient;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.sportdataapi.data.MatchStatus.ENDED;
import static com.sportdataapi.data.MatchStatus.NOT_STARTED;

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
        SdaClient client = SdaClientFactory.newClient("");
        LeaguesClient leaguesClient = client.soccer().leagues();
        League EPL = leaguesClient.get(777);
//        TeamsClient teamsClient = client.soccer().teams();
//        List<Team> EplTeams = teamsClient.list(EPL.getCountryId());
//        SeasonsClient seasonsClient = client.soccer().seasons();
//        List<Season> eplSeason = seasonsClient.list(237);
        MatchesClient matchesClient = client.soccer().matches();
        List<Match> eplMatches = matchesClient.list(1980, NOT_STARTED);

        for (int i = 0; i <eplMatches.size(); i++) {
//            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), EplTeams.get(i).getName());
//            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.valueOf(EplTeams.get(i).getId()));
            if(eplMatches.get(i).getStatus().equals(NOT_STARTED) &&
                    eplMatches.get(i).getHomeTeam().getId() == 2523 |
                            eplMatches.get(i).getGuestTeam().getId() == 2523) {
                String result = eplMatches.get(i).getHomeTeam().getName() + " - " + eplMatches.get(i).getGuestTeam().getName() +
                        "\nStart " + eplMatches.get(i).getStart().getTime();
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), result);
                break;
            }
        }

    }
}
