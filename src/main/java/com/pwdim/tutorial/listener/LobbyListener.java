package com.pwdim.tutorial.listener;

import com.pwdim.tutorial.TUTORIAL;
import com.pwdim.tutorial.utils.ColorUtils;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
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
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ArrayList<UUID> staffVanished = plugin.getVanishedPlayers();

        player.setFlying(player.hasPermission("lobby.vip"));
        player.setWalkSpeed(0.4f);

        String JOIN_MSG =
                "&c&l&m-----&6&l&m-----&e&l&m-----&a&l&m-----&b&l&m-----&9&l&m-----&r\n" +
                        "%s entrou no servidor! \n" +
                "&c&l&m-----&6&l&m-----&e&l&m-----&a&l&m-----&b&l&m-----&9&l&m-----&r";

        if (player.hasPermission("lobby.vip") && !staffVanished.contains(player.getUniqueId())) {
            event.setJoinMessage(ColorUtils.color(JOIN_MSG, player.getDisplayName()));
        } else if (player.hasPermission("lobby.vip") && staffVanished.contains(player.getUniqueId())){
            event.setJoinMessage(null);
        } else {
            event.setJoinMessage(null);
        }

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

        if(builders.contains(p.getUniqueId())) {
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
    }


}