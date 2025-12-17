package com.pwdim.lobby.commands;

import com.pwdim.lobby.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class StaffChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmg, String label, String[] args) {
        String prefix = ColorUtils.color("&d[STAFF] &r");
        String semPermissao = ColorUtils.color("&cVocê não tem permissão para executar esse comando.");
        String usage = ColorUtils.color("&c/staffchat <mensagem>");
        String msg = String.join(" ", args);

        if (sender instanceof Player) {
            Player p = (Player) sender;
            String senderNick = p.getDisplayName();
            if(!p.hasPermission("staff.chat")) {
                p.sendMessage(semPermissao);
            }

            for (Player staff : Bukkit.getOnlinePlayers()) {
                if (staff.hasPermission("staff.chat")) {
                    staff.sendMessage(ColorUtils.color(prefix + senderNick + "&r: " + msg));
                    Bukkit.getConsoleSender().sendMessage(ColorUtils.color(prefix + senderNick + "&r: " + msg));
                }
            }

            if (args.length == 0) {
                p.sendMessage(usage);
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ColorUtils.color(prefix + "&4&l&oCONSOLE" +"&r: " +msg));
            for (Player staff : Bukkit.getOnlinePlayers()) {
                if (staff.hasPermission("staff.chat")) {
                    staff.sendMessage(ColorUtils.color(prefix + "&4&l&oCONSOLE" +"&r: " +msg));
                }
            }
        }
        return true;
    }
}
