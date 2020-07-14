package me.geek.tom.tomthebot.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import me.geek.tom.tomthebot.command.api.CommandSource;
import me.geek.tom.tomthebot.command.api.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.Color;
import java.util.Arrays;
import java.util.stream.Collectors;

import static me.geek.tom.tomthebot.command.CommandManager.literal;

public class HelpCommand implements ICommand {
    @Override
    public void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(literal("help")
                .executes(ctx -> {
                    String help = Arrays.stream(dispatcher.getAllUsage(dispatcher.getRoot(), ctx.getSource(), true))
                            .map(s->"`"+s+"`")
                            .collect(Collectors.joining("\n"));
                    ctx.getSource().getMessage().getChannel().sendMessage(
                            new EmbedBuilder()
                                    .setColor(Color.BLUE)
                                    .setTitle("Tom_The_Bot help.")
                                    .setDescription(help)
                                    .build()
                    ).queue();

                    return 0;
                }));
    }
}
