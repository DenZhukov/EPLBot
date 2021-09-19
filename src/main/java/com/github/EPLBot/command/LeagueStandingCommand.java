package com.github.EPLBot.command;

import com.github.EPLBot.service.SendBotMessageService;
import com.github.EPLBot.sportapiclient.SportClient;
import com.sportdataapi.data.Team;
import com.sportdataapi.data.TeamStandings;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

public class LeagueStandingCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final SportClient sportClient;
    private final static int ID_SEASON = 1980;
    private final static int ID_COUNTRY = 42;

    public LeagueStandingCommand(SendBotMessageService sendBotMessageService, SportClient sportClient) {
        this.sendBotMessageService = sendBotMessageService;
        this.sportClient = sportClient;
    }

    @Override
    public void execute(Update update) {
        List<TeamStandings> listTeamStanding = sportClient.getSdaClient().soccer().standings().get(ID_SEASON).getTeamStandings();
        List<Team> listTeams = sportClient.getSdaClient().soccer().teams().list(ID_COUNTRY);

        String teamStanding = listTeamStanding.stream()
                .map(teamStandings -> String.format("%s %d\n", getNameTeam(listTeams, teamStandings.getTeamId()), teamStandings.getPoints()))
                .collect(Collectors.joining());

        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), teamStanding);
    }

    private String getNameTeam(List<Team> listTeams, int teamId) {
        return listTeams.stream().filter(team -> team.getId() == teamId).findFirst().get().getName();
    }
}
