package com.pwdim.tutorial.listener;

import com.pwdim.tutorial.TUTORIAL;
import com.pwdim.tutorial.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.UUID;

public class LobbyListener implements Listener {

    private final TUTORIAL plugin;

    public LobbyListener(TUTORIAL plugin) {
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
        Player p = e.getPlayer();
        e.setQuitMessage(null);
        plugin.getVanishedPlayers().remove(p);
    }
    @EventHandler
    public void onPlayerJoined(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (Player vanishedPlayer : plugin.getVanishedPlayers()) {
            p.hidePlayer(vanishedPlayer);
        }
    }
}