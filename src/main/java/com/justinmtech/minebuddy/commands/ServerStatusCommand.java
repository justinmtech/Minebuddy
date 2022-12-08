package com.justinmtech.minebuddy.commands;

import com.justinmtech.minebuddy.util.CommandBuilder;
import com.justinmtech.minebuddy.util.ServerStatus;
import org.bukkit.configuration.file.FileConfiguration;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class ServerStatusCommand implements MessageCreateListener {
    private final FileConfiguration config;
    private final ServerStatus status;

    public ServerStatusCommand(FileConfiguration config, ServerStatus status) {
        this.config = config;
        this.status = status;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equalsIgnoreCase(CommandBuilder.build(Command.STATUS))) {
            new MessageBuilder()
                    .append(getConfig().getString("server-status", "Here is the server status:"))
                    .appendCode("", getStatusMessage())
                    .send(event.getChannel());
        }
    }

    private String getStatusMessage() {
        String serverName = getStatus().getName();
        String serverVersion = "Version: " + getStatus().getVersion();
        String serverPlayers = "Players: " + getStatus().getPlayerCount();
        String serverUptime = "Uptime: " + getStatus().getUptime();
        String uniqueJoins = "Unique Players: " + getStatus().getUniqueJoins();
        String ramUsage = "Ram Usage: " + getStatus().getRamUsage() + " (MB)";
        String serverTps = "TPS (1m, 5m, 15m): " + getStatus().getAverageTps();
        String staffOnline = "Staff Online: " + getStatus().getStaffOnline();
        return serverName + "\n" +
                serverVersion + "\n" +
                serverPlayers + "\n" +
                uniqueJoins + "\n" +
                serverUptime + "\n" +
                ramUsage + "\n" +
                serverTps + "\n" +
                staffOnline + "\n";
    }

    public ServerStatus getStatus() {
        return status;
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
