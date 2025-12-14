package com.pwdim.tutorial.commands;

import com.pwdim.tutorial.utils.ColorUtils;
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

        Player p = (Player) sender;

        if(!p.hasPermission("staff.chat")) {
            p.sendMessage(semPermissao);
            return true;
        }

        String senderNick = p.getDisplayName();
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("staff.chat")) {
                staff.sendMessage(ColorUtils.color(prefix + senderNick +"&r: " +msg));
                return true;
            }
            if (!(sender instanceof Player)) {
                staff.sendMessage(ColorUtils.color(prefix + "&4&lCONSOLE &r:" + msg));
                return true;
            }

        }

        if (args.length == 0) {
            p.sendMessage(usage);
            return true;
        }
        return true;
    }

}
