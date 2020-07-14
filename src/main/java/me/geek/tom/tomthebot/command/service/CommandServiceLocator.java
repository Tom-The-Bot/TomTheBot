package me.geek.tom.tomthebot.command.service;

import com.google.common.collect.Lists;
import me.geek.tom.tomthebot.command.api.ICommand;

import java.util.List;
import java.util.ServiceLoader;

public class CommandServiceLocator {
    public static List<ICommand> locateAllCommands() {
        ServiceLoader<ICommand> commandLoader = ServiceLoader.load(ICommand.class);
        return Lists.newArrayList(commandLoader.iterator());
    }
}
