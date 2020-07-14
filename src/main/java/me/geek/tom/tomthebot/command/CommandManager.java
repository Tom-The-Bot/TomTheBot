package me.geek.tom.tomthebot.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.geek.tom.tomthebot.command.api.CommandException;
import me.geek.tom.tomthebot.command.api.CommandSource;
import me.geek.tom.tomthebot.command.api.ICommand;
import me.geek.tom.tomthebot.helper.EmbedHelper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.List;

public class CommandManager extends ListenerAdapter {
    private CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();

    public CommandManager(List<ICommand> commands) {
        commands.forEach(cmd->cmd.register(dispatcher));
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (msg.getContentRaw().startsWith(",")) {
            try {
                executeCommand(msg, event.getChannel());
            } catch (CommandSyntaxException | CommandException e) {
                event.getChannel().sendMessage(EmbedHelper.createError(e)).queue();
            }
        }
    }

    private void executeCommand(Message message, TextChannel channel) throws CommandSyntaxException {
        dispatcher.execute(message.getContentRaw().substring(1), new CommandSource(message, channel));
    }

    public static LiteralArgumentBuilder<CommandSource> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    public static <T> RequiredArgumentBuilder<CommandSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }
}
