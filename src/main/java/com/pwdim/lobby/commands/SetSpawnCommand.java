package com.pwdim.lobby.commands;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.ColorUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    private final LOBBY plugin;

    public SetSpawnCommand(LOBBY plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (sender instanceof Player) {
            Player p = ((Player) sender).getPlayer();

             Location spawn = p.getLocation();
             Location spawnVip = p.getLocation();

             String type = args[0];

             if (p.hasPermission("staff.setspawn")) {

                 if (type.equals("normal")) {
                     LOBBY.spawnLocation.put(p.getWorld(), spawn);
                     p.getWorld().setSpawnLocation(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
                     plugin.saveSpawnsData();
                     sender.sendMessage(ColorUtils.color("&aSpawn de &e&o"+p.getWorld().getName()+ " &adefinido como &b"+ spawn.getBlockX()+", " + spawn.getBlockY()+ ", "+ spawn.getBlockZ()));
                 } else if (type.equals("vip")){
                     LOBBY.spawnVipLocation.put(p.getWorld(), spawnVip);
                     plugin.saveSpawnsData();
                     sender.sendMessage(ColorUtils.color("&aSpawnVip de &e&o"+p.getWorld().getName()+ " &adefinido como &b"+ spawn.getBlockX()+", " + spawn.getBlockY()+ ", "+ spawn.getBlockZ()));

                 } else {
                     sender.sendMessage("&cUse: /setspawn <vip/normal>");
                 }
             }
        } else {
            sender.sendMessage(ColorUtils.color("&cVocê precisa ser um jogador para executar esse comando."));
        }



        return true;
    }
}
