package com.justinmtech.minebuddy.commands;

import com.justinmtech.minebuddy.util.CommandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class BroadcastCommand implements MessageCreateListener {
    private final FileConfiguration config;

    public BroadcastCommand(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().contains(CommandBuilder.build(Command.BROADCAST))) {

            String message = event.getMessageContent().replace("!broadcast ", "");
            int onlinePlayers = Bukkit.getOnlinePlayers().size();

            new MessageBuilder()
                    .append("The following message was broadcast to " + onlinePlayers + " players: \n" + message)
                    .send(event.getChannel());

            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("broadcast-prefix", "&6[Broadcast] &e")+ message));
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
