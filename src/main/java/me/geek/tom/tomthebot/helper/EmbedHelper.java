package me.geek.tom.tomthebot.helper;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;

public class EmbedHelper {

    public static MessageEmbed createError(Exception e) {
        String description = "**There was an error:**\n" + e.getMessage();
        if (e.getCause() != null)
            description += "\nCaused by: " + e.getCause().getMessage();
        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("Tom_The_Bot error!")
                .setDescription(description);
        return builder.build();
    }

}
