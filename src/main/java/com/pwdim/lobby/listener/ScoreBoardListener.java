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
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        FastBoard board = plugin.getBoards().remove(p.getUniqueId());
        if (board != null) {
            board.delete();
        }


        plugin.updateOnline();
    }
}
