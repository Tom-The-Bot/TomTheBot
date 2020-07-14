package me.geek.tom.tomthebot.command.commands;

import com.jagrosh.jdautilities.menu.Paginator;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.geek.tom.tomthebot.TomTheBot;
import me.geek.tom.tomthebot.command.api.CommandException;
import me.geek.tom.tomthebot.command.api.CommandSource;
import me.geek.tom.tomthebot.command.api.ICommand;
import me.geek.tom.tomthebot.helper.GithubScripts;
import me.geek.tom.tomthebot.scripts.ScriptExecutor;
import net.dv8tion.jda.api.exceptions.PermissionException;

import javax.script.ScriptException;
import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static me.geek.tom.tomthebot.command.CommandManager.argument;
import static me.geek.tom.tomthebot.command.CommandManager.literal;

public class ScriptCommand implements ICommand {
    @Override
    public void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(literal("script")
                .then(literal("exec")
                    .then(argument("name", StringArgumentType.string())
                        .executes(ctx -> {
                            try {
                                String script = GithubScripts.getScript(getString(ctx, "name"));
                                ScriptExecutor.executeScript(script, ctx.getSource());
                            } catch (IOException | ScriptException e) {
                                throw new CommandException("Failed to fetch script!", e);
                            }

                            return 0;
                        })
                    )
                ).then(literal("list")
                        .executes(ctx -> {
                            try {
                                List<String> scripts = GithubScripts.listScripts(ctx.getSource().getChannel().getGuild().getId());
                                Paginator.Builder builder = new Paginator.Builder()
                                        .setEventWaiter(TomTheBot.EVENT_WAITER)             // event thing
                                        .setColumns(1)                                      // Only one column pls
                                        .setItemsPerPage(15)                                // 15 items means less pages
                                        .waitOnSinglePage(false)                            // Should it immediatly timeout if there is one page
                                        .useNumberedItems(true)                             // No item numbers pls
                                        .showPageNumbers(true)                              // What page are you on?
                                        .setTimeout(60, TimeUnit.SECONDS)            // Don't keep the pager alive for too long.
                                        .setColor(Color.ORANGE)                             // Green!
                                        .setText("Available scripts:")                      // Title, is in the message text above the embed
                                        .setUsers(ctx.getSource().getMessage().getAuthor()) // Only the original user pls
                                        .setFinalAction(m -> {                              // Cleanup
                                            try {
                                                m.clearReactions().queue();
                                            } catch (PermissionException e) {
                                                m.delete().queue();
                                            } });
                                builder.clearItems();
                                scripts.forEach(builder::addItems);

                                builder.build().paginate(ctx.getSource().getMessage().getChannel(), 1);
                            } catch (IOException e) {
                                throw new CommandException("Failed to fetch scripts!", e);
                            }

                            return 0;
                        })
                )
        );
    }
}
