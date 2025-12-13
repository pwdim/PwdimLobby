package com.pwdim.tutorial.events;

import com.pwdim.tutorial.utils.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class LobbyListener implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setFlying(player.hasPermission("lobby.vip"));
        player.setWalkSpeed(0.4f);

        String JOIN_MSG =
                "&c&l&m-----&6&l&m-----&e&l&m-----&a&l&m-----&b&l&m-----&9&l&m-----&r\n" +
                        "%s entrou no servidor! \n" +
                        "&c&l&m-----&6&l&m-----&e&l&m-----&a&l&m-----&b&l&m-----&9&l&m-----&r";

        if (player.hasPermission("lobby.vip")) {
            event.setJoinMessage(ColorUtils.color(JOIN_MSG, player.getDisplayName()));
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
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public static void onAdmin (PlayerJoinEvent e){

        Player player = e.getPlayer();

        if (player.equals("pwdim")) {
            player.sendMessage(ColorUtils.color("&4&o o adm é top"));
        }

    }
}