package me.geek.tom.tomthebot.command.api;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandSource {

    private final Message message;
    private final TextChannel channel;

    public CommandSource(Message message, TextChannel channel) {
        this.message = message;
        this.channel = channel;
    }

    public Message getMessage() {
        return message;
    }

    public TextChannel getChannel() {
        return channel;
    }
}
