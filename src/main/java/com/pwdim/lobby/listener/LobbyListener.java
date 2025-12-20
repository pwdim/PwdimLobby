package com.pwdim.lobby.listener;

import com.pwdim.lobby.LOBBY;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;


import java.util.ArrayList;
import java.util.UUID;

import static com.pwdim.lobby.LOBBY.reciverList;

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
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        plugin.loadPlayersData();
        plugin.savePlayersData();

    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        FileConfiguration config = Bukkit.getPluginManager().getPlugin("Lobby").getConfig();


        String line1 = config.getString("server.motd.line1", "Servidor Minecraft");
        String line2 = config.getString("server.motd.line2", "Seja bem-vindo!");

        e.setMotd(centralize(line1) + "\n" + centralize(line2));
    }

    private String centralize(String text) {
        if (text == null || text.isEmpty()) return "";

        int totalLength = 52;
        String stripped = ChatColor.stripColor(text);
        int padding = (totalLength - stripped.length()) / 2;

        if (padding <= 0) return text;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        return sb.append(text).toString();
    }


}