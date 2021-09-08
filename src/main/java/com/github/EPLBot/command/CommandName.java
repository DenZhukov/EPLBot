package com.github.EPLBot.command;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    NO("noCommand"),
    TEST("/test"),
    LAST_MATCH("/last_match"),
    NEXT_MATCH("/next_match"),
    STOP("/stop");

    private final String commandName;

    CommandName(String commandName){
        this.commandName=commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
