package com.pwdim.lobby.listener;

import com.pwdim.lobby.LOBBY;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


import java.util.ArrayList;
import java.util.UUID;

public class LobbyListener implements Listener {

    private final LOBBY plugin;

    public LobbyListener(LOBBY plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        ArrayList<UUID> builders = plugin.getBuilders();

        if (builders.contains(p.getUniqueId())) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        ArrayList<UUID> builders = plugin.getBuilders();

        if(builders.contains(p.getUniqueId())) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        plugin.loadPlayersData();
        plugin.savePlayersData();

    }
    @EventHandler
    public void onPlayerJoined(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (Player vanishedPlayer : plugin.getVanishedPlayers()) {
            if (!p.hasPermission("staff.vanish")) {
                p.hidePlayer(vanishedPlayer);
            }
        }


    }

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        plugin.loadPlayersData();
        plugin.savePlayersData();

    }


}