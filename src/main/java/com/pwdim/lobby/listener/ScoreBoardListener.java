package com.pwdim.lobby.listener;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.ColorUtils;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreBoardListener implements Listener {
    private final LOBBY plugin;
    private final Map<UUID, FastBoard> boards = new HashMap<>();


    public ScoreBoardListener(LOBBY plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
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

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        FastBoard board = plugin.getBoards().remove(p.getUniqueId());
        if (board != null) {
            board.delete();
        }
        plugin.updateOnline();
        p.setDisplayName(plugin.getPlayerPrefix(p) + p.getName());
        p.setPlayerListName(plugin.getPlayerPrefix(p) + p.getName());
        p.setCustomName(plugin.getPlayerPrefix(p)+ p.getName());
        p.setCustomNameVisible(true);
    }
}
