package com.pwdim.lobby.commands.staff;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.ConfigUtils;
import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    private final LOBBY plugin;

    public StaffChatCommand(LOBBY plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmg, String label, String[] args) {
        String prefix = MyUtils.color("&d[STAFF] &r");
        String semPermissao = ConfigUtils.noPermMessage();
        String usage = MyUtils.color("&c/staffchat <mensagem>");
        String msg = String.join(" ", args);

        MyUtils.commandPermissionChecker(sender, "staff.chat");
        if (!(sender instanceof Player)){
            if (args.length == 0) {
                sender.sendMessage(usage);
            } else {
                Bukkit.getConsoleSender().sendMessage(MyUtils.color(prefix + "&4&l&oCONSOLE" +"&r: " +msg));
                for (Player staff : Bukkit.getOnlinePlayers()) {
                    if (staff.hasPermission("staff.chat") && plugin.getStaffChatOn().contains(staff.getUniqueId())) {
                        staff.sendMessage(MyUtils.color(prefix + "&4&l&oCONSOLE" +"&r: " +msg));
                    }
                }
            }
        }
        Player p = (Player) sender;
        String senderNick = p.getDisplayName();
        if(!p.hasPermission("staff.chat")) {
            p.sendMessage(semPermissao);
        }

        if (args.length == 0 && plugin.getStaffChatOn().contains(p.getUniqueId()) ) {
            if (plugin.getStaffChatSync().contains(p.getUniqueId())) {
                plugin.getStaffChatSync().remove(p.getUniqueId());
                p.sendMessage(MyUtils.color("&cVocê saiu do staff chat!"));
            } else {
                plugin.getStaffChatSync().add(p.getUniqueId());
                p.sendMessage(MyUtils.color("&aVocê está no staff chat!"));
            }

        } else if (args.length == 0 && !(plugin.getStaffChatOn().contains(p.getUniqueId()))) {
            p.sendMessage(MyUtils.color("&eO seu staff-chat está desativado!"));
        } else {
            if (!plugin.getStaffChatOn().contains(p.getUniqueId())){
                p.sendMessage(MyUtils.color("&eO seu staff-chat está desativado!"));
            }

            MyUtils.broadcastStaff(prefix + senderNick + "&r: " + msg);
        }

        return true;
    }
} 