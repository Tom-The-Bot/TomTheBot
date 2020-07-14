package me.geek.tom.tomthebot;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.geek.tom.tomthebot.command.CommandManager;
import me.geek.tom.tomthebot.command.api.ICommand;
import me.geek.tom.tomthebot.command.service.CommandServiceLocator;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import java.util.List;

public class TomTheBot {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final EventWaiter EVENT_WAITER = new EventWaiter();

    private List<ICommand> commands;

    private void run(String[] args) {
        LOGGER.info("Starting TomTheBot...");
        runSetup();
        LOGGER.info("Connecting to discord...");
        try {
            JDA jda = JDABuilder
                    .createDefault(args[0]) // That token is now invalid!
                    .addEventListeners(EVENT_WAITER, new CommandManager(commands))
                    .build();

            jda.awaitReady();
        } catch (LoginException e) {
            LOGGER.fatal("Failed to connect to Discord!", e);
            return;
        } catch (InterruptedException e) {
            LOGGER.fatal("Interrupted while waiting for ready!", e);
            return;
        }
    }

    private void runSetup() {
        commands = CommandServiceLocator.locateAllCommands();
    }

    public static void main(String[] args) {
        new TomTheBot().run(args);
    }
}
