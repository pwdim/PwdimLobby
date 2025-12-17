package com.pwdim.lobby.commands;

import com.pwdim.lobby.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        float parseArgs = Float.parseFloat(args[0]);
        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas jogadores podem executar esse comando");
            return true;
        } else {
            if (sender.hasPermission("staff.gamemode")||(sender.isOp())) {

                if (parseArgs == 0|| args[0].contains("survival")||args[0].contains("s")) {
                    ((Player) sender).setGameMode(GameMode.SURVIVAL);
                    Bukkit.getConsoleSender().sendMessage(ColorUtils.color("&7&o["+ ((Player) sender).getCustomName() + "&7&o entrou no survival]"));
                    for (Player staff : Bukkit.getOnlinePlayers()) {
                        if (staff.hasPermission("staff.log")) {
                            staff.sendMessage(ColorUtils.color("&7&o["+ ((Player) sender).getCustomName() + "&7&o entrou no survival]"));
                            return true;
                        }
                        return true;
                    }
                    return true;

                } else if (parseArgs == 1|| args[0].contains("creative")||args[0].contains("c")) {
                    ((Player) sender).setGameMode(GameMode.CREATIVE);
                    Bukkit.getConsoleSender().sendMessage(ColorUtils.color("&7&o["+ ((Player) sender).getCustomName() + "&7&o entrou no criativo]"));
                    for (Player staff : Bukkit.getOnlinePlayers()) {
                        if (staff.hasPermission("staff.log")) {
                            staff.sendMessage(ColorUtils.color("&7&o["+ ((Player) sender).getCustomName() + "&7&o entrou no criativo]"));
                            return true;
                        }
                        return true;
                    }
                    return true;
                } else if (parseArgs == 3|| args[0].contains("spectator")||args[0].contains("spec")) {
                    ((Player) sender).setGameMode(GameMode.SPECTATOR);
                    Bukkit.getConsoleSender().sendMessage(ColorUtils.color("&7&o["+ ((Player) sender).getCustomName() + "&7&o entrou no spectator]"));
                    for (Player staff : Bukkit.getOnlinePlayers()) {
                        if (staff.hasPermission("staff.log")) {
                            staff.sendMessage(ColorUtils.color("&7&o["+ ((Player) sender).getCustomName() + "&7&o entrou no spectator]"));
                            return true;
                        }
                        return true;
                    }
                    return true;
                } else if (parseArgs == 2|| args[0].contains("adventure")||args[0].contains("a")) {
                    ((Player) sender).setGameMode(GameMode.ADVENTURE);
                    Bukkit.getConsoleSender().sendMessage(ColorUtils.color("&7&o["+ ((Player) sender).getCustomName() + "&7&o entrou no adventure]"));
                    for (Player staff : Bukkit.getOnlinePlayers()) {
                        if (staff.hasPermission("staff.log")) {
                            staff.sendMessage(ColorUtils.color("&7&o["+ ((Player) sender).getCustomName() + "&7&o entrou no adventure]"));
                            return true;
                        }
                        return true;
                    }
                    return true;
                } else {
                    ((Player) sender).setGameMode(GameMode.CREATIVE);
                    Bukkit.getConsoleSender().sendMessage(ColorUtils.color("&7&o["+ ((Player) sender).getCustomName() + "&7&o entrou no criativo]"));
                    for (Player staff : Bukkit.getOnlinePlayers()) {
                        if (staff.hasPermission("staff.log")) {
                            staff.sendMessage(ColorUtils.color("&7&o["+ ((Player) sender).getCustomName() + "&7&o entrou no criativo]"));
                            return true;
                        }
                        return true;
                    }
                    return true;
                }


            } else {
                sender.sendMessage(ColorUtils.color("Você não possui permissão para executar esse comando!"));
                return true;
            }


        }
    }
}
