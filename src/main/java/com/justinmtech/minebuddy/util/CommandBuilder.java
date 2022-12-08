package com.justinmtech.minebuddy.util;

import com.justinmtech.minebuddy.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class CommandBuilder {

    public static String build(Command command) {
        return getConfig().getString("command-symbol", "!") +
                getConfig().getString("command-prefix", "") +
                getConfig().getString("commands." + command.name());
    }

    public static FileConfiguration getConfig() {
        return Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Minebuddy")).getConfig();
    }
}
