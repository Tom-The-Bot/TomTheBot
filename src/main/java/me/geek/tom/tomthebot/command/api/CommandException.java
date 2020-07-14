package me.geek.tom.tomthebot.command.api;

public class CommandException extends RuntimeException {
    public CommandException(String msg) {
        super(msg);
    }

    public CommandException(String msg, Throwable t) {
        super(msg, t);
    }
}
