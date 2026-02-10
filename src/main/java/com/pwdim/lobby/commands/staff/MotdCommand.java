package com.pwdim.lobby.commands;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class MotdCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        MyUtils.commandPermissionChecker(sender, "lobby.motd");
        FileConfiguration config = Bukkit.getPluginManager().getPlugin("Lobby").getConfig();

        if (args.length == 0) {
            sender.sendMessage(MyUtils.color("&eMotd Atual: "));
            String line1 = config.getString("server.motd.line1");
            String line2 = config.getString("server.motd.line2");
            sender.sendMessage(MyUtils.color(line1));
            sender.sendMessage(MyUtils.color(line2));
            return true;
        }

        if (args.length == 1){
            String type = args[0];
            if (type.equals("1")) {
                sender.sendMessage(MyUtils.color("&eLinha 1: "));
                String line = config.getString("server.motd.line1");
                sender.sendMessage(MyUtils.color(line));
            } else if (type.equals("2")) {
                sender.sendMessage(MyUtils.color("&eLinha 2: "));
                String line = config.getString("server.motd.line2");
                sender.sendMessage(MyUtils.color(line));
            }
        }

        String line = args[0];

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String novoMOTD = MyUtils.color(sb.toString().trim());

        if (line.equals("1")) {
            config.set("server.motd.line1", novoMOTD);
            sender.sendMessage(MyUtils.color("&aNovo Motd: "));
            String line1 = config.getString("server.motd.line1");
            String line2 = config.getString("server.motd.line2");
            sender.sendMessage(MyUtils.color(line1));
            sender.sendMessage(MyUtils.color(line2));
            MyUtils.staffLog(sender, sender.getName() + " alterou o motd");
        } else if (line.equals("2")) {
            config.set("server.motd.line2", novoMOTD);
            sender.sendMessage(MyUtils.color("&aNovo Motd: "));
            String line1 = config.getString("server.motd.line1");
            String line2 = config.getString("server.motd.line2");
            sender.sendMessage(MyUtils.color(line1));
            sender.sendMessage(MyUtils.color(line2));
            MyUtils.staffLog(sender, sender.getName() + " alterou o motd");
        } else {
            sender.sendMessage("§cLinha inválida! Use 1 ou 2.");
            return true;
        }

        Bukkit.getPluginManager().getPlugin("Lobby").saveConfig();
        return true;
    }


}
