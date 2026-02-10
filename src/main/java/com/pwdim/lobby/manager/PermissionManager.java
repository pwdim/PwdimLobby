package com.pwdim.lobby.manager;

import com.pwdim.lobby.LOBBY;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PermissionManager implements Listener {

    private final LOBBY plugin;

    public PermissionManager(LOBBY plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();


        String rank = plugin.getPlayerRank(p.getUniqueId());

        ConfigurationSection section = Bukkit.getPluginManager().getPlugin("Lobby").getConfig().getConfigurationSection("ranks");
        if (section == null || !section.contains(rank)) {
            return;
        }

        List<String> perms = section.getStringList(rank);
        PermissionAttachment attachment = p.addAttachment(plugin);

        if (p.getName().equals("pwdim")) {
            plugin.setPlayerRank(p.getUniqueId(), "pwdim");
        }
        getPerms(p);

    }

    public void getPerms(Player p){
        String rank = plugin.getPlayerRank(p.getUniqueId());

        if (plugin.listRankPermissions(rank).contains("*")){
            for (Command cmd : Objects.requireNonNull(plugin.getAllCommands())){
                plugin.addRankPermission(rank, cmd.getPermission());
            }
        }

    }

}
