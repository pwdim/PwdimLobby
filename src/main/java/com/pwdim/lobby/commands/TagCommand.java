package com.pwdim.lobby.commands;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TagCommand implements CommandExecutor {

    private final LOBBY plugin;

    public TagCommand(LOBBY plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigurationSection section = Bukkit.getPluginManager().getPlugin("Lobby").getConfig().getConfigurationSection("tags");

        if(sender instanceof Player) {
            Player p = (Player) sender;


            if (args.length == 0) {
                p.sendMessage(ColorUtils.color("&aSuas Tags: "));

                for (String key : section.getKeys(false)) {
                    String perm = section.getString(key + ".permission");

                    if (p.hasPermission(perm)||perm.isEmpty()) {
                        String prefix = section.getString(key + ".type");

                        p.sendMessage(ColorUtils.color(prefix));

                    }
                }
            } else {
                String selectedTag = args[0].toLowerCase();

                String perm = section.getString(selectedTag + ".permission");
                String prefix = section.getString(selectedTag + ".prefix");
                String color = section.getString(selectedTag + ".name-color");

                if (section.contains(selectedTag) && p.hasPermission(perm)) {
                    LOBBY.playerTag.put(p.getUniqueId(), selectedTag);
                    p.setDisplayName(ColorUtils.color(prefix +p.getName()));
                    p.setCustomName(ColorUtils.color(color +p.getName()));
                    p.setPlayerListName(ColorUtils.color(prefix +p.getName()));
                    p.sendMessage(ColorUtils.color("&aTag alterada para "+ prefix+"&r"));
                    plugin.setNameTag(p);

                } else {
                    p.sendMessage(ColorUtils.color("&cVocê não tem essa tag ou ela não existe!"));
                }

            }

        } else {

            if (args.length == 0) {
                sender.sendMessage(ColorUtils.color("&cUse: /tag <player> <tag>"));
            } else {
                String nick = args[0];
                Player p = Bukkit.getPlayer(nick);

                String selectedTag = args[1].toLowerCase();
                String perm = section.getString(selectedTag + ".permission");
                String prefix = section.getString(selectedTag + ".prefix");
                String color = section.getString(selectedTag + ".name-color");

                for (Player online : Bukkit.getOnlinePlayers()) {
                    if(online.getName().contains(p.getName())) {


                        if (p.hasPermission(perm)) {
                            LOBBY.playerTag.put(p.getUniqueId(), selectedTag);
                            p.setDisplayName(ColorUtils.color(prefix +p.getName()));
                            p.setCustomName(ColorUtils.color(color +p.getName()));
                            p.setPlayerListName(ColorUtils.color(prefix +p.getName()));
                            p.sendMessage(ColorUtils.color("&aTag alterada para "+ prefix+"&r"));

                            sender.sendMessage(ColorUtils.color("&aTag de " + p.getName() + " alterada para " + prefix));
                            plugin.setNameTag(p);
                        } else {
                            sender.sendMessage(ColorUtils.color("&cO jogador não tem essa tag!"));
                        }

                    } else {
                        sender.sendMessage(ColorUtils.color("&cO jogador não está online."));
                    }
                }
            }
        }


        plugin.savePlayersData();

        return true;
    }
}
