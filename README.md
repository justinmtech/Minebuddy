# Minebuddy

A Minecraft plugin utility that allows you to host a Discord bot. The bot has basic commands such as broadcast and stop, and a variety of server status information such as TPS, player count, unique joins and ram usage.

# Is this for me?
If you run a Minecraft server and would like a lightweight plugin to see your server's status straight from your Discord server, you may find this useful.

# Why Minebuddy?
Minebuddy is extremely simple and configurable, and it can be used on 1.13+.

# Setup
1. Create a Discord bot here if you don't already have one: https://discord.com/developers/applications/
2. Download and place the plugin JAR file into your plugins folder.
3. Reboot your server and open the config in plugins/Minebuddy/config.yml
4. Set the token value to your bot's private token (do not share this token!)
5. Boot the server
6. If the bot is not already in your server, invite it with the link prompted in the console startup
7. Try using "!help" on your Discord server
8. If it works, you're done! Feel free to change the configuration to suit your needs.

# Commands
Command syntax is determined like this: "{commandSymbol}{commandPrefix}{commandName}"
You can change the symbol, prefix and command name in the config.
- BROADCAST: Broadcast a message to all online players
- PLAYERS: View all online players (except players with permission corresponding with "invisible-on-list-permission")
- STATUS: View a variety of server info such as unique joins, ram usage and tps
- STOP: Executes the stop command
- HELP: View all bot commands
