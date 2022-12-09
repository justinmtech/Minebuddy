package com.justinmtech.minebuddy.notifications;

import com.justinmtech.minebuddy.Minebuddy;
import com.justinmtech.minebuddy.util.ServerStatus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Notifier implements Listener {
    private final Minebuddy plugin;
    private final ServerStatus status;
    private final FileConfiguration config;
    private final Logger logger;
    private double lastTpsCheck;

    public Notifier(Minebuddy plugin) {
        this.plugin = plugin;
        this.status = plugin.getStatus();
        this.config = plugin.getConfig();
        this.logger = plugin.getLogger();
        setLastTpsCheck(20.0);
        run();
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                double tps = getStatus().getTpsFromPastMinuteAsDouble();
                if (isTpsLow()) {
                    setLastTpsCheck(tps);
                    sendDiscordAlert(getStatus().getName() + " TPS is currently " + tps + "! :cry:");
                } else if (!isTpsLow() && getLastTpsCheck() < getTpsThreshold()) {
                    sendDiscordAlert(getStatus().getName() + " TPS has recovered! :cowboy:");
                    setLastTpsCheck(tps);
                }
            }
        }.runTaskTimerAsynchronously(getPlugin(), 1200L, 1200L);
    }

    private boolean isTpsLow() {
        double tps = getStatus().getTpsFromPastMinuteAsDouble();
        double threshold = getTpsThreshold();
        return tps < threshold;
    }

    private double getTpsThreshold() {
        return getConfig().getDouble("alerts.tps-threshold", 15.0);
    }

    public void sendDiscordAlert(String message) {
        if (getAlertChannel().isPresent()) {
            new MessageBuilder()
                    .append(message)
                    .send(getAlertChannel().get());
        }
    }

    private Optional<TextChannel> getAlertChannel() {
        String channelId = getConfig().getString("alerts.channel-id", "");
        if (channelId == null) return Optional.empty();
        if (channelId.equals("")) getLogger().log(Level.SEVERE, "Alerts are enabled but you have not set a channel ID!");
        return getPlugin().getApi().getTextChannelById(channelId);
    }

    private Minebuddy getPlugin() {
        return plugin;
    }

    public ServerStatus getStatus() {
        return status;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public Logger getLogger() {
        return logger;
    }

    public double getLastTpsCheck() {
        return lastTpsCheck;
    }

    public void setLastTpsCheck(double lastTpsCheck) {
        this.lastTpsCheck = lastTpsCheck;
    }
}
