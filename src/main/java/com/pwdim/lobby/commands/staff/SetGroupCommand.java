package com.pwdim.lobby.commands.staff;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetGroupCommand implements CommandExecutor {
    private final LOBBY plugin;

    public SetGroupCommand(LOBBY plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length < 2) {
                p.sendMessage(MyUtils.color("&cUse: /setgroup <jogador> <grupo>"));
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (target == null) { p.sendMessage(MyUtils.color("&cJogador não encontrado."));}
            String group = args[1];
            if (group == null) {
                p.sendMessage(MyUtils.color("&cGrupo não encontrado."));
            }
            assert target != null;
            plugin.setPlayerRank(target.getUniqueId(), group);
            assert group != null;
            p.sendMessage(MyUtils.color("&aGrupo de &b" + target.getName() + " &adefinido como &4" + group.toUpperCase()));
            plugin.savePlayersData();
        } else {

            if (args.length < 2) {
                sender.sendMessage(MyUtils.color("&cUse: /setgroup <jogador> <grupo>"));
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (target == null) { sender.sendMessage(MyUtils.color("&cJogador não encontrado."));}
            String group = args[1];
            if (group == null) {
                sender.sendMessage(MyUtils.color("&cGrupo não encontrado."));
            }
            assert target != null;
            plugin.setPlayerRank(target.getUniqueId(), group);
            assert group != null;
            sender.sendMessage(MyUtils.color("&aGrupo de &b" + target.getName() + " &adefinido como &4" + group.toUpperCase()));
            plugin.savePlayersData();
        }
        return true;
    }
}
