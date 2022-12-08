package com.justinmtech.minebuddy.commands;

import com.justinmtech.minebuddy.util.CommandBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class ListCommands implements MessageCreateListener {
    private final FileConfiguration config;

    public ListCommands(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().contains(CommandBuilder.build(Command.HELP))) {
        String commandSymbol = getConfig().getString("command-symbol", "!");
            new MessageBuilder()
                    .append(getHelpFirstLine(commandSymbol))
                    .appendCode("", getHelpMessage(commandSymbol))
                    .send(event.getChannel());
        }
    }

    private String getHelpMessage(String commandSymbol) {
        String commands =
                CommandBuilder.build(Command.STATUS) + " - View server status \n" +
                CommandBuilder.build(Command.PLAYERS) + " - View online players by name \n" +
                CommandBuilder.build(Command.BROADCAST) + " - Broadcast a message to the server \n" +
                CommandBuilder.build(Command.STOP) + " - Shut down the server \n" +
                CommandBuilder.build(Command.HELP) + " - View help commands \n";

        if (commandSymbol != null) commands = commands.replace(commandSymbol, "");
        return commands;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    private String getHelpFirstLine(String commandSymbol) {
        String firstLine = getConfig().getString("messages.help", "The following commands can be prefixed with '" + commandSymbol + "' to be executed:");
        if (firstLine != null) {
            firstLine = firstLine.replace("%placeholder%", commandSymbol);
        }
        return firstLine;
    }
}
