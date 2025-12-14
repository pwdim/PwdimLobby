package com.pwdim.tutorial.listener;

import com.pwdim.tutorial.TUTORIAL;
import com.pwdim.tutorial.utils.ColorUtils;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ScoreBoardListener implements Listener {
    private final TUTORIAL plugin;
    private final Map<UUID, FastBoard> boards = new HashMap<>();

    public ScoreBoardListener(TUTORIAL plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        FastBoard board = new FastBoard(p);
        String title = ColorUtils.color("&B&LPWDIM");
        String footer = ColorUtils.color("&b&oplay.pwdim.com");
        String online = ColorUtils.color("&fOnline: &b&o"+ plugin.getOnlinePlayers());

        board.updateTitle(title);
        board.updateLine(0, ColorUtils.color(" "));
        board.updateLine(1, ColorUtils.color("&fOlá, %s.", p.getCustomName()));
        board.updateLine(2, ColorUtils.color(" "));
        board.updateLine(3, ColorUtils.color(online));
        board.updateLine(4, ColorUtils.color(" "));
        board.updateLine(5, footer);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        FastBoard board = new FastBoard(p);

        board.delete();
    }
}
