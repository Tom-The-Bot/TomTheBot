package me.geek.tom.tomthebot.command.api;

import com.mojang.brigadier.CommandDispatcher;

public interface ICommand {
    void register(CommandDispatcher<CommandSource> dispatcher);
}
