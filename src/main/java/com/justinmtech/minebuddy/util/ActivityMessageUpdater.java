package com.justinmtech.minebuddy.util;

import com.justinmtech.minebuddy.Minebuddy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.user.UserStatus;

public class ActivityMessageUpdater {
    private final Minebuddy plugin;

    public ActivityMessageUpdater(Minebuddy plugin) {
        this.plugin = plugin;
        run();
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                String playersOnline = getStatus().getPlayerCount();
                String tps = getStatus().getTpsFromPastMinute();
                String ramUsage = getStatus().getRamUsage();
                String activityMessage = playersOnline + " " + tps + " " + ramUsage;
                getDiscordApi().updateActivity(ActivityType.WATCHING, activityMessage);
                updateBotStatus(Integer.parseInt(tps.replace("*", "")));
            }
        }.runTaskTimerAsynchronously(getPlugin(), 320L, 320L);
    }

    private void updateBotStatus(int pastMinuteTps) {
        if (pastMinuteTps >= 17) {
            getDiscordApi().updateStatus(UserStatus.ONLINE);
        }
        else if (pastMinuteTps >= 15) {
            getDiscordApi().updateStatus(UserStatus.IDLE);
        } else {
            getDiscordApi().updateStatus(UserStatus.DO_NOT_DISTURB);
        }
    }

    public Minebuddy getPlugin() {
        return plugin;
    }

    public DiscordApi getDiscordApi() {
        return getPlugin().getApi();
    }

    public ServerStatus getStatus() {
        return getPlugin().getStatus();
    }

    public FileConfiguration getConfig() {
        return getPlugin().getConfig();
    }
}
