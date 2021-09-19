package com.github.EPLBot.command;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    NO("noCommand"),
    TEST("/test"),
    LAST_MATCH("/last_match"),
    LAST_5_MATCHES("/last_5_matches"),
    NEXT_MATCH("/next_match"),
    STAT("/stat"),
    ADD_TEAM("/addteam"),
    STANDING("/standing"),
    STOP("/stop");

    private final String commandName;

    CommandName(String commandName){
        this.commandName=commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
