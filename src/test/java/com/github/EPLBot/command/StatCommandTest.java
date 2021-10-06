package com.github.EPLBot.command;

import static com.github.EPLBot.command.CommandName.STAT;

public class StatCommandTest extends AbstractCommandTest{

    @Override
    String getCommandName() {
        return STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return "0";
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendBotMessageService, telegramUserService);
    }
}
