package me.geek.tom.tomthebot.scripts;

import com.google.common.collect.Lists;
import me.geek.tom.tomthebot.command.api.CommandSource;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.script.ScriptException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ScriptExecutor {

    public static void executeScript(String script, CommandSource src) throws ScriptException {
        List<String> lines = Lists.newArrayList(script.split("\n"));
        for (String line : lines) {
            execLine(line, src);
        }
    }

    public static void execLine(String line, CommandSource src) throws ScriptException {
        if (line.startsWith("#")) return;

        String[] pts = line.split(" ");
        switch (pts[0]) {
            case "TYPE":
                try {
                    runType(pts, src);
                } catch (InterruptedException e) {
                    throw new ScriptException("Error on line: " + line + ": " + e.getMessage());
                }
                break;
            case "REPLY":
                reply(pts, src);
                break;
            case "REACT":
                react(pts, src);
                break;
            default:
                throw new ScriptException("Not a valid script on line: " + line);
        }
    }

    private static void runType(String[] pts, CommandSource src) throws InterruptedException {
        String time = pts[1];
        int secs = Integer.parseInt(time);
        MessageChannel channel = src.getMessage().getChannel();
        channel.sendTyping().queue();
        Thread.sleep(secs * 1000);
    }

    private static void reply(String[] pts, CommandSource src) {
        String msg = Arrays.stream(pts).skip(1).collect(Collectors.joining(" "));
        src.getMessage().getChannel().sendMessage(msg).queue();
    }

    private static void react(String[] pts, CommandSource src) {
        String reaction = pts[1];
        src.getMessage().addReaction(reaction).queue();
    }
}
