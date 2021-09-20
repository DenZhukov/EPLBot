package com.github.EPLBot.command;

import com.github.EPLBot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.EPLBot.command.CommandName.*;

public class HelpCommand implements Command{
    private final SendBotMessageService sendBotMessageService;

    private final static String HELP_MESSAGE = String.format("✨<b>Available commands</b>✨\n" +
                    "\n %s - get started," +
                    "\n %s - finish work," +
                    "\n %s - show next match," +
                    "\n %s - show last match," +
                    "\n %s - show last 5 matches," +
                    "\n %s - show last round" +
                    "\n %s - show next round" +
                    "\n %s - show league standings" +
                    "\n %s - you already asked for it...",
            START.getCommandName(), STOP.getCommandName(), NEXT_MATCH.getCommandName(),
            LAST_MATCH.getCommandName(), LAST_5_MATCHES.getCommandName(), LAST_ROUND.getCommandName(),
            NEXT_ROUND.getCommandName(), STANDING.getCommandName() ,HELP.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
