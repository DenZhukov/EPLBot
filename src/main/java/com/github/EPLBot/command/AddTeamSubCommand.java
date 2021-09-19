package com.github.EPLBot.command;

import com.github.EPLBot.repository.entity.TelegramUser;
import com.github.EPLBot.service.SendBotMessageService;
import com.github.EPLBot.service.TelegramUserService;
import com.github.EPLBot.sportapiclient.SportClient;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.stream.Collectors;

import static com.github.EPLBot.command.CommandName.ADD_TEAM;
import static com.sportdataapi.data.MatchStatus.ENDED;

public class AddTeamSubCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final SportClient sportClient;
    private final TelegramUserService telegramUserService;
    private final static int ID_SEASON = 1980;

    public AddTeamSubCommand(SendBotMessageService sendBotMessageService, SportClient sportClient, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.sportClient = sportClient;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        if(update.getMessage().getText().equalsIgnoreCase(ADD_TEAM.getCommandName())) {
            sendTeamList(update.getMessage().getChatId());
            return;
        }
        String teamId = update.getMessage().getText().split(" ")[1];
        Long chatId = update.getMessage().getChatId();
        if(NumberUtils.isDigits(teamId)){
            telegramUserService.findByChatId(chatId).ifPresentOrElse(
                    user -> {
                        user.setTeamId(Integer.valueOf(teamId));
                        telegramUserService.save(user);
                    }, () -> {
                        TelegramUser telegramUser = new TelegramUser();
                        telegramUser.setActive(true);
                        telegramUser.setChatId(chatId);
                        telegramUser.setTeamId(Integer.valueOf(teamId));
                        telegramUserService.save(telegramUser);
                    }
            );
        }
    }

    private void sendTeamList(Long chatId){
        String matchesId = sportClient.getMatchesClient().list(ID_SEASON, ENDED).stream()
                .filter(round -> round.getRoundId() == 35014)
                .map(team -> String.format("%s - %s\n%s - %s\n", team.getHomeTeam().getName(), team.getHomeTeam().getId(), team.getGuestTeam().getName(), team.getGuestTeam().getId()))
                .collect(Collectors.joining());

        String message = "Choose your team!\n\n" +
                "For instance: /addteam 2523\n" +
                "List of teams for you:\n" +
                "%s";

        sendBotMessageService.sendMessage(chatId.toString(), String.format(message, matchesId));
    }
}
