package com.pwdim.lobby.commands;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.ColorUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SpawnCommand implements CommandExecutor {

    private final LOBBY plugin;

    public SpawnCommand(LOBBY plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = ((Player) sender).getPlayer();

            p.teleport(plugin.getWorldSpawn(p.getWorld()));

            p.playSound(p.getLocation(), Sound.PORTAL_TRAVEL, 1.0f, 1.0f);



        } else {
            sender.sendMessage(ColorUtils.color("&cVocê precisa ser um jogador para executar esse comando!"));
        }


        return true;
    }
}
