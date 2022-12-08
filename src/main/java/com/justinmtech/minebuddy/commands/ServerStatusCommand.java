package com.justinmtech.minebuddy.commands;

import com.justinmtech.minebuddy.util.CommandBuilder;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class ServerStatusCommand implements MessageCreateListener {
    private final FileConfiguration config;

    public ServerStatusCommand(FileConfiguration config) {
        this.config = config;
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
        String serverName = getConfig().getString("server-name", "A Minecraft Server");
        String serverVersion = PlaceholderAPI.setPlaceholders(null, "Version: " + "%server_version%");
        String serverPlayers = PlaceholderAPI.setPlaceholders(null, "Players: " + "%server_online%/%server_max_players%");
        String serverUptime = PlaceholderAPI.setPlaceholders(null, "Uptime: " + "%server_uptime%");
        String uniqueJoins = PlaceholderAPI.setPlaceholders(null, "Unique Players: " + "%server_unique_joins%");
        String ramUsage = PlaceholderAPI.setPlaceholders(null, "Ram Usage: " + "%server_ram_used%/%server_ram_total% (MB)");
        String serverTps = PlaceholderAPI.setPlaceholders(null, "TPS (1m, 5m, 15m): %server_tps_1%, %server_tps_5%, %server_tps_15%");
        String staffOnline = "Staff Online: " + getStaffOnline();
        return serverName + "\n" +
                serverVersion + "\n" +
                serverPlayers + "\n" +
                uniqueJoins + "\n" +
                serverUptime + "\n" +
                ramUsage + "\n" +
                serverTps + "\n" +
                staffOnline + "\n";
    }

    //Get online staff member count (must have permission of staff-permission's value)
    private int getStaffOnline() {
        String permission = getConfig().getString("staff-permission");
        if (permission == null) return 0;
        int count = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(permission)) count++;
        }
        return count;
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
