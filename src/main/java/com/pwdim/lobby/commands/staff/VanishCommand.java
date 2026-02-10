package com.pwdim.lobby.commands.staff;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class VanishCommand implements CommandExecutor {
    private final LOBBY plugin;

    public VanishCommand(LOBBY plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmg, String label, String[] args) {
        String semPermissao = MyUtils.color("&cVocê não tem permissão para executar esse comando.");
        ArrayList<UUID> staffVanished = plugin.getVanishedPlayers();
        ArrayList<UUID> staffVisualize = plugin.getStaffVisualize();

        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas jogadores podem usar este comando.");
            return false;
        } else {
            Player staff = (Player) sender;
            if (!staff.hasPermission("staff.vanish")) {
                staff.sendMessage(semPermissao);
                return true;
            } else if (staff.hasPermission("staff.vanish") && staffVanished.contains(staff.getUniqueId()))
                if (args.length == 0) {
                    Bukkit.getConsoleSender().sendMessage(MyUtils.color("&7&o[" + ((Player) sender).getCustomName() + "&7&o saiu do vanish.]"));
                    staffVanished.remove(staff.getUniqueId());
                    staff.sendMessage(MyUtils.color("&dVocê saiu do vanish."));
                    staff.setGameMode(GameMode.SURVIVAL);
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.showPlayer(staff);
                        staff.showPlayer(player);
                        if (player.hasPermission("staff.log") && !player.equals(staff)) {
                            player.sendMessage(MyUtils.color("&7&o[" + ((Player) sender).getCustomName() + "&7&o saiu do vanish.]"));
                        }

                    }
                } else {
                    if (args[0].contains("vl")||args[0].contains("visualize")) {
                        if (staffVisualize.contains(staff.getUniqueId())) {
                            Bukkit.getConsoleSender().sendMessage(MyUtils.color("&7&o[" + ((Player) sender).getCustomName() + "&7&o saiu do visualize.]"));
                            staff.sendMessage(MyUtils.color("&dOs jogadores não podem mais te ver."));
                            staff.setGameMode(GameMode.CREATIVE);
                            staffVisualize.remove(staff.getUniqueId());
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.hidePlayer(staff);
                                staff.showPlayer(player);
                                if (player.hasPermission("staff.log") && !player.equals(staff)) {
                                    player.sendMessage(MyUtils.color("&7&o[" + ((Player) sender).getCustomName() + "&7&o saiu do visualize.]"));
                                }
                            }
                        } else {
                            Bukkit.getConsoleSender().sendMessage(MyUtils.color("&7&o[" + ((Player) sender).getCustomName() + "&7&o entrou no visualize.]"));
                            staff.sendMessage(MyUtils.color("&dOs jogadores podem te ver."));
                            staff.setGameMode(GameMode.CREATIVE);
                            staffVisualize.add(staff.getUniqueId());
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.showPlayer(staff);
                                staff.showPlayer(player);
                                if (player.hasPermission("staff.log") && !player.equals(staff)) {
                                    player.sendMessage(MyUtils.color("&7&o[" + ((Player) sender).getCustomName() + "&7&o entrou no visualize.]"));
                                }
                            }
                        }
                    } else {
                        staff.sendMessage(MyUtils.color("&cUse: /v <vl, visualize>"));
                    }

                }
            else if (staff.hasPermission("staff.vanish") && !staffVanished.contains(staff.getUniqueId())) {
                staffVanished.add(staff.getUniqueId());
                Bukkit.getConsoleSender().sendMessage(MyUtils.color("&7&o[" +((Player) sender).getCustomName() + "&7&o entrou no vanish.]"));
                staff.sendMessage(MyUtils.color("&dVocê entrou do vanish."));
                staff.setGameMode(GameMode.CREATIVE);

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.hidePlayer(staff);
                    if (player.hasPermission("staff.log") && !player.equals(staff)) {
                        player.sendMessage(MyUtils.color("&7&o[" +((Player) sender).getCustomName() + "&7&o entrou no vanish.]"));
                    }

                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("staff.vanish")) {
                        player.showPlayer(staff);
                        staff.showPlayer(player);
                    } else {
                        player.hidePlayer(staff);
                        staff.showPlayer(player);
                    }
                }
            }

        }
        return true;
    }

}