package com.pwdim.lobby.commands.staff;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;


public class GroupCommand implements CommandExecutor {

    private final LOBBY plugin;

    public GroupCommand(LOBBY plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if ((sender instanceof Player)) {
            CommandSender console = sender;

            ConfigurationSection section = plugin.getConfig().getConfigurationSection("ranks");
            if (section == null) {
                console.sendMessage(MyUtils.color("&cErro: Seção 'ranks' não encontrada no config.yml"));
                return true;
            }


            if (args.length == 0) {
                console.sendMessage(MyUtils.color("&eLista de Ranks: "));
                for (String s : section.getKeys(false)) {
                    console.sendMessage(MyUtils.color("&7- &4" + s.toUpperCase()));
                }
                return true;
            }

            String rankTarget = args[0];


            if (args.length == 1) {
                if (!section.contains(rankTarget)) {
                    console.sendMessage(MyUtils.color("&eRank inválido."));
                    return true;
                }

                console.sendMessage(MyUtils.color("&ePermissões do rank &4" + rankTarget.toUpperCase() + "&e:"));
                for (String perm : section.getStringList(rankTarget)) {
                    console.sendMessage(MyUtils.color("&7- &a" + perm));
                }
                return true;
            }

            if (args.length == 3) {
                String action = args[1].toLowerCase();
                String targetPermission = args[2].toLowerCase();

                if (!section.contains(rankTarget)) {
                    console.sendMessage(MyUtils.color("&cRank inexistente."));
                    return true;
                }

                if (action.equals("add")) {
                    plugin.addRankPermission(rankTarget, targetPermission);
                    console.sendMessage(MyUtils.color("&eAdicionada: &a" + targetPermission + " &ano rank &4" + rankTarget.toUpperCase()));
                } else if (action.equals("remove")) {
                    plugin.removeRankPermission(rankTarget, targetPermission);
                    console.sendMessage(MyUtils.color("&eRemovida: &c" + targetPermission + " &ano rank &4" + rankTarget.toUpperCase()));
                } else {
                    console.sendMessage(MyUtils.color("&cUse: /group <rank> <add/remove> <permissão>"));
                    return true;
                }


                plugin.saveConfig();
                plugin.reloadConfig();

                return true;
            }

            console.sendMessage(MyUtils.color("&cUse: /group <rank> <add/remove> <permissão>"));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("staff.group")) {
            p.sendMessage(plugin.noPermMessage());
            return true;
        }

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("ranks");
        if (section == null) {
            p.sendMessage(MyUtils.color("&cErro: Seção 'ranks' não encontrada no config.yml"));
            return true;
        }


        if (args.length == 0) {
            p.sendMessage(MyUtils.color("&eLista de Ranks: "));
            for (String s : section.getKeys(false)) {
                p.sendMessage(MyUtils.color("&7- &4" + s.toUpperCase()));
            }
            return true;
        }

        String rankTarget = args[0];


        if (args.length == 1) {
            if (!section.contains(rankTarget)) {
                p.sendMessage(MyUtils.color("&eRank inválido."));
                return true;
            }

            p.sendMessage(MyUtils.color("&ePermissões do rank &4" + rankTarget.toUpperCase() + "&e:"));
            for (String perm : section.getStringList(rankTarget)) {
                p.sendMessage(MyUtils.color("&7- &a" + perm));
            }
            return true;
        }

        if (args.length == 3) {
            String action = args[1].toLowerCase();
            String targetPermission = args[2].toLowerCase();

            if (!section.contains(rankTarget)) {
                p.sendMessage(MyUtils.color("&cRank inexistente."));
                return true;
            }

            if (action.equals("add")) {
                plugin.addRankPermission(rankTarget, targetPermission);
                p.sendMessage(MyUtils.color("&aAdicionada: &e" + targetPermission + " &ano rank &4" + rankTarget.toUpperCase()));
            } else if (action.equals("remove")) {
                plugin.removeRankPermission(rankTarget, targetPermission);
                p.sendMessage(MyUtils.color("&aRemovida: &e" + targetPermission + " &ano rank &4" + rankTarget.toUpperCase()));
            } else {
                p.sendMessage(MyUtils.color("&cUse: /group <rank> <add/remove> <permissão>"));
                return true;
            }


            plugin.saveConfig();
            plugin.reloadConfig();

            return true;
        }

        p.sendMessage(MyUtils.color("&cUse: /group <rank> <add/remove> <permissão>"));
        return true;
    }
}