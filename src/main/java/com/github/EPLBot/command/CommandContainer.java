package com.github.EPLBot.command;

import com.github.EPLBot.service.SendBotMessageService;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

import static com.github.EPLBot.command.CommandName.*;

public class CommandContainer {

    private final Command unknownCommand;
    private final ImmutableMap<String, Command> commandMap;

    public CommandContainer (SendBotMessageService sendBotMessageService) {
        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(TEST.getCommandName(), new TestCommand(sendBotMessageService))
                .put(LAST_MATCH.getCommandName(), new LastMatchCommand(sendBotMessageService))
                .put(NEXT_MATCH.getCommandName(), new NextMatchCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

}
