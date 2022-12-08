package com.justinmtech.minebuddy.commands;

import com.justinmtech.minebuddy.util.CommandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Objects;
import java.util.stream.Collectors;

public class PlayersOnlineCommand implements MessageCreateListener {
    private final FileConfiguration config;

    public PlayersOnlineCommand(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (!event.getMessageContent().equalsIgnoreCase(CommandBuilder.build(Command.PLAYERS))) {
            return;
        }

        String onlinePlayers = getOnlinePlayers();

        if (onlinePlayers.isEmpty()) {
            event.getChannel().sendMessage(getConfig().getString("messages.no-players-online", "There are no players online."));
            return;
        }

        sendDiscordMessage(onlinePlayers, event);
    }

    private void sendDiscordMessage(String onlinePlayers, MessageCreateEvent event) {
        new MessageBuilder()
                .append(getConfig().getString("messages.player-list", "The following players are online:"))
                .appendCode("", onlinePlayers)
                .send(event.getChannel());
    }

    private String getOnlinePlayers() {
        return Bukkit.getOnlinePlayers()
                .stream().filter(p -> !p.hasPermission
                        (Objects.requireNonNull
                                (getConfig().getString("invisible-on-list-permission", ""))))
                .map(Player::getName)
                .collect(Collectors.joining(", "));
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
