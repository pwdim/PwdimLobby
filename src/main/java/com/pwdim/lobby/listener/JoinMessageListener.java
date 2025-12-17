package com.pwdim.lobby.listener;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.ColorUtils;
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

    private final LOBBY plugin;

    public JoinMessageListener(LOBBY plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        p.setDisplayName(plugin.getPlayerPrefix(p) + p.getName());
        p.setPlayerListName(plugin.getPlayerPrefix(p) + p.getName());
        p.setCustomName(plugin.getPlayerColor(p)+ p.getName());
        p.setCustomNameVisible(true);
        plugin.setNameTag(p);

        ArrayList<Player> vanishedList = plugin.getVanishedPlayers();
        Location spawn = new Location(p.getWorld(), 21, 33, -13);
        Location spawnVip = new Location(p.getWorld(), 21, 35, -13);

        String JOIN_MSG =
                "&c&l&m-----&6&l&m-----&e&l&m-----&a&l&m-----&b&l&m-----&9&l&m-----&r\n" +
                "%s entrou no servidor! \n" +
                "&c&l&m-----&6&l&m-----&e&l&m-----&a&l&m-----&b&l&m-----&9&l&m-----&r";


        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000, 1, false, false));
        if(p.hasPermission("lobby.vip")) {
            p.setAllowFlight(true);
            if (!p.isFlying()){p.setFlying(true);}
            p.teleport(spawnVip);

            if (!(vanishedList.contains(p))) {
                e.setJoinMessage(ColorUtils.color(JOIN_MSG, p.getDisplayName()));
                p.setGameMode(GameMode.ADVENTURE);
            } else {
                p.setGameMode(GameMode.CREATIVE);
                e.setJoinMessage("");
            }
        } else {
            e.setJoinMessage("");
            p.setFlying(false);
            p.setGameMode(GameMode.ADVENTURE);
            p.teleport(spawn);
        }


        for (Player target : Bukkit.getOnlinePlayers()) {

            if (vanishedList.contains(target)) {

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
