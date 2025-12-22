package com.pwdim.lobby.commands;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private final LOBBY plugin;

    public SpawnCommand(LOBBY plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = ((Player) sender).getPlayer();



            if (p.hasPermission("lobby.vip")) {
                p.teleport(plugin.getWorldVipSpawn(p.getWorld()));
            } else {
                p.teleport(plugin.getWorldSpawn(p.getWorld()));
            }



        } else {
            sender.sendMessage(ColorUtils.color("&cVocê precisa ser um jogador para executar esse comando!"));
        }


        return true;
    }
}
