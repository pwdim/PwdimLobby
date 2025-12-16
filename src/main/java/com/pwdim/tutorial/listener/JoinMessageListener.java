package com.pwdim.tutorial.listener;

import com.pwdim.tutorial.TUTORIAL;
import com.pwdim.tutorial.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class JoinMessageListener implements Listener {

    private final TUTORIAL plugin;
    public JoinMessageListener(TUTORIAL plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Location spawn = new Location(p.getWorld(), 21, 33, -13);
        Location spawnVip = new Location(p.getWorld(), 21, 35, -13);

        String JOIN_MSG =
                "&c&l&m-----&6&l&m-----&e&l&m-----&a&l&m-----&b&l&m-----&9&l&m-----&r\n" +
                "%s entrou no servidor! \n" +
                "&c&l&m-----&6&l&m-----&e&l&m-----&a&l&m-----&b&l&m-----&9&l&m-----&r";


        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000, 1, false, false));
        ArrayList<Player> vanishedList = plugin.getVanishedPlayers();
        if(p.hasPermission("lobby.vip")) {

            if (!plugin.getVanishedPlayers().contains(p)) {
                e.setJoinMessage(ColorUtils.color(JOIN_MSG, p.getDisplayName()));
                p.setGameMode(GameMode.ADVENTURE);
            } else {
                e.setJoinMessage("");
            }
            p.teleport(spawnVip);
            p.setAllowFlight(true);
            p.setFlying(true);
        } else {
            e.setJoinMessage("");
            p.setFlying(false);
            p.setGameMode(GameMode.ADVENTURE);
            p.teleport(spawn);
        }


        for (Player target : Bukkit.getOnlinePlayers()) {

            if (plugin.getVanishedPlayers().contains(target)) {

                if (!p.hasPermission("staff.vanish")) {
                    p.hidePlayer(target);
                } else {
                    p.showPlayer(target);
                    p.showPlayer(p);
                    target.showPlayer(p);
                    target.showPlayer(target);
                }
            }
        }
    }
}
