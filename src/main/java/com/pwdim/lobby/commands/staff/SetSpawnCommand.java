package com.pwdim.lobby.commands.staff;


import com.pwdim.lobby.LOBBY;

import com.pwdim.lobby.utils.MyUtils;

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


            Location spawn = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getLocation().getPitch(), p.getLocation().getYaw());

            Location spawnVip = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getLocation().getPitch(), p.getLocation().getYaw());


            String type = args[0];


            if (p.hasPermission("staff.setspawn")) {


                if (type != null && type.equals("normal")) {

                    LOBBY.spawnLocation.put(p.getWorld(), spawn);

                    plugin.saveSpawnsData();

                    sender.sendMessage(MyUtils.color("&aSpawn de &e&o"+p.getWorld().getName()+ " &adefinido como &b"+ spawn.getBlockX()+", " + spawn.getBlockY()+ ", "+ spawn.getBlockZ()));

                } else if (type != null && type.equals("vip")){

                    LOBBY.spawnVipLocation.put(p.getWorld(), spawnVip);

                    plugin.saveSpawnsData();

                    sender.sendMessage(MyUtils.color("&aSpawnVip de &e&o"+p.getWorld().getName()+ " &adefinido como &b"+ spawn.getBlockX()+", " + spawn.getBlockY()+ ", "+ spawn.getBlockZ()));


                } else if (type == null){

                    sender.sendMessage("&cUse: /setspawn <vip/normal>");

                }

            }

        } else {

            sender.sendMessage(MyUtils.color("&cVocê precisa ser um jogador para executar esse comando."));

        }




        return true;

    }

} 