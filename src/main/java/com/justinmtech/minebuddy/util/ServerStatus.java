package com.justinmtech.minebuddy.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ServerStatus {
    private final FileConfiguration config;

    public ServerStatus(FileConfiguration config) {
        this.config = config;
    }

    public String getName() {
        return getConfig().getString("server-name", "A Minecraft server");
    }

    public String getVersion() {
        return getPlaceholders("%server_version%");
    }

    public String getPlayerCount() {
        return getPlaceholders("%server_online%/%server_max_players%");
    }

    public String getUptime() {
        return getPlaceholders("%server_uptime%");
    }

    public String getUniqueJoins() {
        return getPlaceholders("%server_unique_joins%");
    }

    public String getRamUsage() {
        return getPlaceholders("%server_ram_used%/%server_ram_total%");
    }

    public String getAverageTps() {
        return getPlaceholders("%server_tps_1%, %server_tps_5%, %server_tps_15%");
    }

    public String getTpsFromPastMinute() {
        return getPlaceholders("%server_tps_1%");
    }

    public double getTpsFromPastMinuteAsDouble() {
        String tpsString = getPlaceholders("%server_tps_1%");
        if (tpsString.contains("*")) tpsString = tpsString.replace("*", "");
        double tps;
        try {
            tps = Double.parseDouble(tpsString);
        } catch (NumberFormatException e) {
            return 5.0;
        }
        return tps;
    }


    public int getStaffOnline() {
        String permission = getConfig().getString("staff-permission");
        if (permission == null) return 0;
        int count = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(permission)) count++;
        }
        return count;
    }

    private String getPlaceholders(String placeholder) {
        return PlaceholderAPI.setPlaceholders(null, placeholder);
    }

    private FileConfiguration getConfig() {
        return config;
    }
}
