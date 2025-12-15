package com.pwdim.tutorial.commands;

import com.pwdim.tutorial.TUTORIAL;
import com.pwdim.tutorial.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class VanishCommand implements CommandExecutor {
    private final TUTORIAL plugin;

    public VanishCommand(TUTORIAL plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmg, String label, String[] args) {
        String semPermissao = ColorUtils.color("&cVocê não tem permissão para executar esse comando.");
        ArrayList<Player> staffVanished = plugin.getVanishedPlayers();

        Player staff = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas jogadores podem usar este comando.");
            return false;
        }

        if (!staff.hasPermission("staff.vanish")) {
            staff.sendMessage(semPermissao);
            return true;
        } else if (staff.hasPermission("staff.vanish") && staffVanished.contains(staff)) {
            staffVanished.remove(staff);
            staff.sendMessage(ColorUtils.color("&dVocê saiu do vanish."));
            Bukkit.getOnlinePlayers();
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.showPlayer(staff);
                if (player.hasPermission("staff.log") && !player.equals(staff)) {
                    player.sendMessage("&7&o[" +((Player) sender).getCustomName() + "&7&osaiu do vanish.]");
                }
            }
        } else if (staff.hasPermission("staff.vanish") && !staffVanished.contains(staff)) {
            staffVanished.add(staff);
            staff.sendMessage(ColorUtils.color("&dVocê entrou do vanish."));
            Bukkit.getOnlinePlayers().remove(staff);
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.hidePlayer(staff);
                if (player.hasPermission("staff.log") && !player.equals(staff)) {
                    player.sendMessage("&7&o[" +((Player) sender).getCustomName() + "&7&oentrou do vanish.]");
                }
            }
        }
        return true;
    }

}
