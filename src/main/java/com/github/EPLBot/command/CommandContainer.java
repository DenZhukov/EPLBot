package com.github.EPLBot.command;

import com.github.EPLBot.service.SendBotMessageService;
import com.github.EPLBot.service.TelegramUserService;
import com.github.EPLBot.sportapiclient.SportClient;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

import static com.github.EPLBot.command.CommandName.*;

public class CommandContainer {

    private final Command unknownCommand;
    private final ImmutableMap<String, Command> commandMap;

    public CommandContainer (SendBotMessageService sendBotMessageService, SportClient sportClient, TelegramUserService telegramUserService) {
        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(TEST.getCommandName(), new TestCommand(sendBotMessageService))
                .put(LAST_MATCH.getCommandName(), new LastMatchCommand(sendBotMessageService, sportClient, telegramUserService))
                .put(NEXT_MATCH.getCommandName(), new NextMatchCommand(sendBotMessageService, sportClient, telegramUserService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(ADD_TEAM.getCommandName(), new AddTeamSubCommand(sendBotMessageService, sportClient, telegramUserService))
                .put(LAST_5_MATCHES.getCommandName(), new LastFiveMatchCommand(sendBotMessageService, sportClient, telegramUserService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

}
