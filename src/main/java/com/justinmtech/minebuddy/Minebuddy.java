package com.justinmtech.minebuddy;

import com.justinmtech.minebuddy.commands.*;
import com.justinmtech.minebuddy.util.ActivityMessageUpdater;
import com.justinmtech.minebuddy.util.ServerStatus;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.entity.user.UserStatus;

public final class Minebuddy extends JavaPlugin {
    private DiscordApi api;
    private ServerStatus status;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new DiscordApiBuilder()
                .setToken(getToken())
                .addIntents(Intent.MESSAGE_CONTENT)
                .login()
                .thenAccept(this::onConnectToDiscord)
                .exceptionally(error -> {
                    getLogger().warning("The bot failed to connect to Discord. Plugin disabling!");
                    getPluginLoader().disablePlugin(this);
                    return null;
                });

        setStatus(new ServerStatus(getConfig()));
        new ActivityMessageUpdater(this);
    }

    @Override
    public void onDisable() {
        if (getApi() != null) {
            getApi().disconnect();
            setApi(null);
        }
    }

    private void onConnectToDiscord(DiscordApi api) {
        setApi(api);

        api.updateActivity(ActivityType.WATCHING, getStatus().getName());
        api.updateStatus(UserStatus.DO_NOT_DISTURB);

        getLogger().info("Bot connected to Discord server as: " + api.getYourself().getDiscriminatedName());
        getLogger().info("Go to this link to invite bot to your server: " + api.createBotInvite());

        api.addListener(new ListCommands(getConfig()));
        api.addListener(new PlayersOnlineCommand(getConfig()));
        api.addListener(new BroadcastCommand(getConfig()));
        api.addListener(new StopServerCommand(getConfig()));
        api.addListener(new ServerStatusCommand(getConfig(), getStatus()));

    }

    public String getToken() {
        return getConfig().getString("token", "");
    }

    public ServerStatus getStatus() {
        return status;
    }

    public DiscordApi getApi() {
        return api;
    }

    public void setApi(DiscordApi api) {
        this.api = api;
    }

    public void setStatus(ServerStatus status) {
        this.status = status;
    }
}
