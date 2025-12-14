package com.pwdim.tutorial.commands;

import com.pwdim.tutorial.TUTORIAL;
import com.pwdim.tutorial.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.UUID;

public class BuildCommand implements CommandExecutor {

    private final TUTORIAL plugin;

    public BuildCommand(TUTORIAL plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String semPermissao = ColorUtils.color("&cVocê não tem permissão para executar esse comando.");
        ArrayList<UUID> buildersList = plugin.getBuilders();

        Player builder = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage("Você não pode executar esse comando.");
            return false;
        }

        if (!sender.hasPermission("lobby.build")) {
            sender.sendMessage(semPermissao);
            return true;
        }

        if (builder.hasPermission("lobby.build") && buildersList.contains(builder.getUniqueId())) {
            buildersList.remove(builder.getUniqueId());
            builder.sendMessage(ColorUtils.color("&CBuild desativada!"));
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("staff.log") && !player.equals(builder)) {
                    player.sendMessage("&7&o[" +((Player) sender).getCustomName() + "&7&odesativou o modo build]");
                }
            }
        } else if (builder.hasPermission("lobby.build") && !buildersList.contains(builder.getUniqueId())) {
            buildersList.add(builder.getUniqueId());
            builder.sendMessage(ColorUtils.color("&aBuild ativada!"));
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("staff.log") && !player.equals(builder)) {
                    player.sendMessage("&7&o[" +((Player) sender).getCustomName() + "&7&oativou o modo build]");
                }
            }
        }
        return true;


    }
}
