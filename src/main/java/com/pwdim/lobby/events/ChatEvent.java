package com.pwdim.lobby.events;


import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {
    private final LOBBY plugin;
    public ChatEvent(LOBBY plugin) {
        this.plugin = plugin;
    }

    private static Boolean toggledChat = Boolean.TRUE;

    @EventHandler
    public void asyncChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String prefix = MyUtils.color("&d[STAFF] &r");
        e.setFormat(p.getDisplayName() + MyUtils.color(": &r" + e.getMessage()));

        if (plugin.getStaffChatSync().contains(p.getUniqueId())) {
            e.setCancelled(true);
            for (Player staff : Bukkit.getOnlinePlayers()) {
                if (staff.hasPermission("staff.chat")) {
                    staff.sendMessage(MyUtils.color(prefix + p.getDisplayName() + "&r: " + e.getMessage()));
                    Bukkit.getConsoleSender().sendMessage(MyUtils.color(prefix + p.getDisplayName() + "&r: " + e.getMessage()));
                }
            }
        }
    }

    public static Boolean getToggledChat() {
        return toggledChat;
    }

    public static void setToggledChat(Boolean toggledChat){
        ChatEvent.toggledChat = toggledChat;
    }

} 