package com.pwdim.lobby.commands;

import com.pwdim.lobby.LOBBY;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class MotdCommand implements CommandExecutor {
// 99,99% foi o gemini
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("staff.motd")) {
            sender.sendMessage("§cSem permissão!");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§eUse: /motd <1/2> <motd>");
            return true;
        }

        String line = args[0];

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String novoTexto = ChatColor.translateAlternateColorCodes('&', sb.toString().trim());

        FileConfiguration config = Bukkit.getPluginManager().getPlugin("Lobby").getConfig();

        if (line.equals("1")) {
            config.set("server.motd.line1", novoTexto);
            sender.sendMessage("§aLinha 1 atualizada!");
        } else if (line.equals("2")) {
            config.set("server.motd.line2", novoTexto);
            sender.sendMessage("§aLinha 2 atualizada!");
        } else {
            sender.sendMessage("§cLinha inválida! Use 1 ou 2.");
            return true;
        }

        Bukkit.getPluginManager().getPlugin("Lobby").saveConfig();
        return true;
    }


}
