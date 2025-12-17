package com.pwdim.lobby.events;

import com.pwdim.lobby.utils.ColorUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class ChatEvent implements Listener {

    @EventHandler
    public static void asyncChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        e.setFormat(p.getDisplayName() + ColorUtils.color(": &r" + e.getMessage()));
    }
}
