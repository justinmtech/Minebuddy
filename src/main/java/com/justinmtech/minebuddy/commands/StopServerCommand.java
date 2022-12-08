package com.justinmtech.minebuddy.commands;

import com.justinmtech.minebuddy.util.CommandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class StopServerCommand implements MessageCreateListener {
    private final FileConfiguration config;

    public StopServerCommand(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equalsIgnoreCase(CommandBuilder.build(Command.STOP))) {
            new MessageBuilder()
                    .append(getConfig().getString("messages.stop-server", "The server is shutting down.."))
                    .send(event.getChannel());
            Bukkit.getServer().shutdown();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
