package com.github.EPLBot.command;

import static com.github.EPLBot.command.UnknownCommand.UNKNOWN_MESSAGE;

public class UnKnownCommandTest extends AbstractCommandTest{

    @Override
    String getCommandName() {
        return "/rewrerwer";
    }

    @Override
    String getCommandMessage() {
        return UNKNOWN_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new UnknownCommand(sendBotMessageService);
    }
}
