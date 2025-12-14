package com.pwdim.tutorial.listener;

import com.pwdim.tutorial.TUTORIAL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class VanishListener implements Listener {
    private final TUTORIAL plugin;

    public VanishListener(TUTORIAL plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (Player target : Bukkit.getOnlinePlayers()) {

            if (plugin.getVanishedPlayers().contains(target.getUniqueId())) {


                if (!p.hasPermission("staff.vanish")) {
                    p.hidePlayer(target);
                }
            }
        }

        if (plugin.getVanishedPlayers().contains(p.getUniqueId())) {
            e.setJoinMessage(null);
        }
    }
}

