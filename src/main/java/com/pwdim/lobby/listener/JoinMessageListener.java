package com.pwdim.lobby.listener;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.ColorUtils;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

public class JoinMessageListener implements Listener {

    private final LOBBY plugin;

    public JoinMessageListener(LOBBY plugin) {
        this.plugin = plugin;
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        p.setDisplayName(plugin.getPlayerPrefix(p) + p.getName());
        p.setPlayerListName(plugin.getPlayerPrefix(p) + p.getName());
        p.setCustomName(plugin.getPlayerColor(p)+ p.getName());
        p.setCustomNameVisible(true);
        plugin.setNameTag(p);
        p.setFoodLevel(20);
        p.setMaxHealth(20);
        p.setHealth(20);


        Random random = new Random();


        double coordX = -1.0 + (1.0 - (-1.0)) * random.nextDouble();

        double coordY = -1.0 + (1.0 - (-1.0)) * random.nextDouble();

        Location spawn = new Location(plugin.getServer().getWorld("world"), coordX, 60, coordY, -90, 0);
        Location spawnVip = new Location(plugin.getServer().getWorld("world"), coordX, 62, coordY, -90, 0);

        String JOIN_MSG =
                "&c&l&m-----&6&l&m-----&e&l&m-----&a&l&m-----&b&l&m-----&9&l&m-----&r\n" +
                "%s entrou no servidor! \n" +
                "&c&l&m-----&6&l&m-----&e&l&m-----&a&l&m-----&b&l&m-----&9&l&m-----&r";


        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000, 1, false, false));

        if (plugin.getVanishedPlayers().contains(p)) {
            e.setJoinMessage("");
            p.teleport(spawnVip);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                p.setAllowFlight(true);
                p.setFlying(true);
                p.setGameMode(GameMode.CREATIVE);
            }, 1L);
        }

        else if (p.hasPermission("lobby.vip")) {
            e.setJoinMessage(ColorUtils.color(JOIN_MSG, p.getDisplayName()));
            p.teleport(spawnVip);

            p.setAllowFlight(true);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                p.setFlying(true);
            }, 1L);
        }

        else {
            e.setJoinMessage("");
            p.teleport(spawn);
            p.setAllowFlight(false);
            p.setFlying(false);
        }
        p.showPlayer(p);

        for (Player target : Bukkit.getOnlinePlayers()) {

            if (plugin.getVanishedPlayers().contains(target)) {

                if (!p.hasPermission("staff.vanish")) {
                    p.hidePlayer(target);
                    target.showPlayer(p);
                } else {
                    p.showPlayer(target);
                    p.showPlayer(p);
                    target.showPlayer(p);
                    target.showPlayer(target);
                }
            }
        }

        for (Player vanishedPlayer : plugin.getVanishedPlayers()) {
            if (!p.hasPermission("staff.vanish")) {
                p.hidePlayer(vanishedPlayer);
                p.showPlayer(p);
                vanishedPlayer.showPlayer(p);
                vanishedPlayer.showPlayer(vanishedPlayer);
            }
        }


        FastBoard board = new FastBoard(p);
        String title = ColorUtils.color("&B&LPWDIM");
        String footer = ColorUtils.color("&b&oplay.pwdim.com");
        String online = ColorUtils.color("&fOnline: &b&o" + Bukkit.getOnlinePlayers().size());
        plugin.getBoards().put(p.getUniqueId()  , board);
        String selectTag = plugin.getPlayerTag(p);

        ConfigurationSection section = Bukkit.getPluginManager().getPlugin("Lobby").getConfig().getConfigurationSection("tags");

        String type = section.getString(selectTag + ".type");


        board.updateTitle(title);
        board.updateLine(1, ColorUtils.color("&fRank: %s&f", type));
        board.updateLine(2, ColorUtils.color(" "));
        board.updateLine(3, ColorUtils.color(online));
        board.updateLine(4, ColorUtils.color(" "));
        board.updateLine(5, footer);
    }
}
