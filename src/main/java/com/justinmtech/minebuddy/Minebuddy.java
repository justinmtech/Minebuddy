package com.justinmtech.minebuddy;

import com.justinmtech.minebuddy.commands.*;
import com.justinmtech.minebuddy.util.CommandBuilder;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;

public final class Minebuddy extends JavaPlugin {
    private DiscordApi api;

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
    }

    @Override
    public void onDisable() {
        if (api != null) {
            api.disconnect();
            api = null;
        }
    }

    private void onConnectToDiscord(DiscordApi api) {
        this.api = api;

        getLogger().info("Bot connected to Discord server as: " + api.getYourself().getDiscriminatedName());
        getLogger().info("Go to this link to invite bot to your server: " + api.createBotInvite());

        api.addListener(new ListCommands(getConfig()));
        api.addListener(new PlayersOnlineCommand(getConfig()));
        api.addListener(new BroadcastCommand(getConfig()));
        api.addListener(new StopServerCommand(getConfig()));
        api.addListener(new ServerStatusCommand(getConfig()));

    }

    public String getToken() {
        return getConfig().getString("token", "");
    }
}
