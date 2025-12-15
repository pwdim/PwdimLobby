package com.pwdim.tutorial.listener;

import com.pwdim.tutorial.TUTORIAL;
import com.pwdim.tutorial.utils.ColorUtils;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class LobbyListener implements Listener {

    private final TUTORIAL plugin;

    public LobbyListener(TUTORIAL plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ArrayList<Player> staffVanished = plugin.getVanishedPlayers();
        PotionEffect efeito = new PotionEffect(PotionEffectType.NIGHT_VISION, 100000, 1, false, false);

        String JOIN_MSG = ColorUtils.color(
                "&c&l&m-----&6&l&m-----&e&l&m-----&a&l&m-----&b&l&m-----&9&l&m-----&r\n" +
                        "%s entrou no servidor! \n" +
                        "&c&l&m-----&6&l&m-----&e&l&m-----&a&l&m-----&b&l&m-----&9&l&m-----&r", player.getDisplayName());

        Random random = new Random();
        float randomSpawn = random.nextFloat(-3, 3);
        Location spawn = new Location(Bukkit.getWorld("world"), randomSpawn, 60.0, randomSpawn, 0, 0);
        Location spawnVip = new Location(Bukkit.getWorld("world"), randomSpawn, 63.0, randomSpawn, 0, 0);

        player.setFlying(player.hasPermission("lobby.vip"));
        player.setWalkSpeed(0.2f);
        player.addPotionEffect(PotionEffectType.SATURATION.createEffect(100000, 1));
        player.addPotionEffect(efeito);

        if (player.hasPermission("lobby.vip") && !staffVanished.contains(player)) {
            player.teleport(spawnVip);
            event.setJoinMessage(JOIN_MSG);
        } else if (player.hasPermission("lobby.vip") && staffVanished.contains(player)) {
            player.teleport(spawnVip);
            event.setJoinMessage(null);
        } else if (!player.hasPermission("lobby.vip")) {
            event.setJoinMessage(null);
            player.teleport(spawn);
        } else if (player.hasPermission("lobby.vip")) {
            player.teleport(spawnVip);
            event.setJoinMessage(JOIN_MSG);
        }


        for (Player target : Bukkit.getOnlinePlayers()) {

            if (plugin.getVanishedPlayers().contains(target)) {


                if (!player.hasPermission("staff.vanish")) {
                    player.hidePlayer(target);
                }
            }
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
    }
    @EventHandler
    public void onPlayerJoined(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (Player vanishedPlayer : plugin.getVanishedPlayers()) {
            p.hidePlayer(vanishedPlayer);
        };
    }

}