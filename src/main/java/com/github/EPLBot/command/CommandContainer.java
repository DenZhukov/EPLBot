package com.github.EPLBot.command;

import com.github.EPLBot.service.SendBotMessageService;
import com.github.EPLBot.service.TelegramUserService;
import com.github.EPLBot.sportapiclient.SportClient;
import com.google.common.collect.ImmutableMap;

import static com.github.EPLBot.command.CommandName.*;

public class CommandContainer {

    public final Command unknownCommand;
    private final ImmutableMap<String, Command> commandMap;

    public CommandContainer (SendBotMessageService sendBotMessageService, SportClient sportClient, TelegramUserService telegramUserService) {
        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(LAST_MATCH.getCommandName(), new LastMatchCommand(sendBotMessageService, sportClient, telegramUserService))
                .put(NEXT_MATCH.getCommandName(), new NextMatchCommand(sendBotMessageService, sportClient, telegramUserService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(ADD_TEAM.getCommandName(), new AddTeamSubCommand(sendBotMessageService, sportClient, telegramUserService))
                .put(LAST_5_MATCHES.getCommandName(), new LastFiveMatchCommand(sendBotMessageService, sportClient, telegramUserService))
                .put(STANDING.getCommandName(), new LeagueStandingCommand(sendBotMessageService, sportClient))
                .put(NEXT_ROUND.getCommandName(), new NextRoundCommand(sendBotMessageService, sportClient))
                .put(LAST_ROUND.getCommandName(), new LastRoundCommand(sendBotMessageService, sportClient))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

}
