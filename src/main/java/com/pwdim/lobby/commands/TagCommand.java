package com.pwdim.lobby.commands;


import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

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
                StringBuilder sb = new StringBuilder("&aSuas Tags: ");
                boolean encontrouTag = false;

                for (String key : section.getKeys(false)) {
                    String perm = section.getString(key + ".permission");

                    if (perm.isEmpty() || p.hasPermission(perm)) {
                        String tagVisual = section.getString(key + ".type");

                        if (encontrouTag) {
                            sb.append("&r, ");
                        }

                        sb.append(tagVisual);
                        encontrouTag = true;
                    }
                }


                if (encontrouTag) {
                    p.sendMessage(MyUtils.color(sb.toString()));
                } else {
                    p.sendMessage(MyUtils.color("&cVocê não possui nenhuma tag disponível. &eAdquira mais em &b&oloja.pwdim.com."));
                }

            } else {
                String selectedTag = args[0].toLowerCase();

                String perm = section.getString(selectedTag + ".permission");
                String prefix = section.getString(selectedTag + ".prefix");
                String type = section.getString(selectedTag + ".type");
                String color = section.getString(selectedTag + ".name-color");

                if (section.contains(selectedTag) && p.hasPermission(perm)) {
                    LOBBY.playerTag.put(p.getUniqueId(), selectedTag);
                    p.setDisplayName(MyUtils.color(prefix +p.getName()));
                    p.setCustomName(MyUtils.color(color +p.getName()));
                    p.setPlayerListName(MyUtils.color(prefix +p.getName()));
                    p.sendMessage(MyUtils.color("&aTag alterada para "+ type+"&r"));
                    plugin.setNameTag(p);

                } else {
                    p.sendMessage(MyUtils.color("&cVocê não tem essa tag ou ela não existe!"));
                }
            }
        } else {
            String target = args[0];
            Player p = Bukkit.getPlayer(target);

            if (args.length != 2 || !section.contains(args[1].toLowerCase()) || p == null) {
                sender.sendMessage(MyUtils.color("&cUse: /tag <player> <tag>"));
            } else {
                String selectedTag = args[1].toLowerCase();
                String perm = section.getString(selectedTag + ".permission");
                String prefix = section.getString(selectedTag + ".prefix");
                String color = section.getString(selectedTag + ".name-color");


                if (p.hasPermission(perm)) {
                    LOBBY.playerTag.put(p.getUniqueId(), selectedTag);
                    p.setDisplayName(MyUtils.color(prefix +p.getName()));
                    p.setCustomName(MyUtils.color(color +p.getName()));
                    p.setPlayerListName(MyUtils.color(prefix +p.getName()));
                    p.sendMessage(MyUtils.color("&aTag alterada para "+ prefix+"&r"));

                    sender.sendMessage(MyUtils.color("&aTag de " + p.getName() + " alterada para " + prefix));
                    plugin.setNameTag(p);
                } else {
                    sender.sendMessage(MyUtils.color("&cO jogador não tem essa tag!"));
                }
            }
        }

        plugin.savePlayersData();

        return true;
    }
} 