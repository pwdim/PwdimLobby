package com.pwdim.tutorial.events;

import com.pwdim.tutorial.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.io.BufferedWriter;

public class ChatEvent implements Listener {

    @EventHandler
    public static void asyncChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        e.setFormat(p.getDisplayName() + ColorUtils.color(": &r" + e.getMessage()));
    }

    @EventHandler
    public static void pInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()||p.hasPermission("tag.admin")) {
            p.setDisplayName(ColorUtils.color("&4&lADMIN &4&o") + p.getName());
            p.setPlayerListName(ColorUtils.color("&4&lADMIN &4&o") + p.getName());
            p.setCustomName(ColorUtils.color("&4&lADMIN &4&o") + p.getName());
        } else if (p.hasPermission("tag.modplus")) {
            p.setDisplayName(ColorUtils.color("&5&lMOD+ &5&o") + p.getName());
            p.setPlayerListName(ColorUtils.color("&5&lMOD+ &5&o") + p.getName());
            p.setCustomName(ColorUtils.color("&5&lMOD+ &5&o") + p.getName());
        } else if (p.hasPermission("tag.mod")) {
            p.setDisplayName(ColorUtils.color("&5&lMOD &5&o") + p.getName());
            p.setPlayerListName(ColorUtils.color("&5&lMOD &5&o") + p.getName());
            p.setCustomName(ColorUtils.color("&5&lMOD &5&o") + p.getName());
        } else {
            p.setDisplayName(ColorUtils.color("&7") + p.getName());
            p.setPlayerListName(ColorUtils.color("&7") + p.getName());
            p.setCustomName(ColorUtils.color("&7") + p.getName());
        }
    }
    @EventHandler
    public static void playerChat(AsyncPlayerChatEvent e) {        Player p = e.getPlayer();
        if (p.isOp()||p.hasPermission("tag.admin")) {
            p.setDisplayName(ColorUtils.color("&4&lADMIN &4&o") + p.getName());
            p.setPlayerListName(ColorUtils.color("&4&lADMIN &4&o") + p.getName());
            p.setCustomName(ColorUtils.color("&4&lADMIN &4&o") + p.getName());
        } else if (p.hasPermission("tag.modplus")) {
            p.setDisplayName(ColorUtils.color("&5&lMOD+ &5&o") + p.getName());
            p.setPlayerListName(ColorUtils.color("&5&lMOD+ &5&o") + p.getName());
            p.setCustomName(ColorUtils.color("&5&lMOD+ &5&o") + p.getName());
        } else if (p.hasPermission("tag.mod")) {
            p.setDisplayName(ColorUtils.color("&5&lMOD &5&o") + p.getName());
            p.setPlayerListName(ColorUtils.color("&5&lMOD &5&o") + p.getName());
            p.setCustomName(ColorUtils.color("&5&lMOD &5&o") + p.getName());
        } else {
            p.setDisplayName(ColorUtils.color("&7") + p.getName());
            p.setPlayerListName(ColorUtils.color("&7") + p.getName());
            p.setCustomName(ColorUtils.color("&7") + p.getName());
        }
    }
    @EventHandler
    public static void pLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()||p.hasPermission("tag.admin")) {
            p.setDisplayName(ColorUtils.color("&4&lADMIN &4&o") + p.getName());
            p.setPlayerListName(ColorUtils.color("&4&lADMIN &4&o") + p.getName());
            p.setCustomName(ColorUtils.color("&4&lADMIN &4&o") + p.getName());
        } else if (p.hasPermission("tag.modplus")) {
            p.setDisplayName(ColorUtils.color("&5&lMOD+ &5&o") + p.getName());
            p.setPlayerListName(ColorUtils.color("&5&lMOD+ &5&o") + p.getName());
            p.setCustomName(ColorUtils.color("&5&lMOD+ &5&o") + p.getName());
        } else if (p.hasPermission("tag.mod")) {
            p.setDisplayName(ColorUtils.color("&5&lMOD &5&o") + p.getName());
            p.setPlayerListName(ColorUtils.color("&5&lMOD &5&o") + p.getName());
            p.setCustomName(ColorUtils.color("&5&lMOD &5&o") + p.getName());
        } else {
            p.setDisplayName(ColorUtils.color("&7") + p.getName());
            p.setPlayerListName(ColorUtils.color("&7") + p.getName());
            p.setCustomName(ColorUtils.color("&7") + p.getName());
        }
    }
    @EventHandler
    public static void pMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (p.isOp()||p.hasPermission("tag.admin")) {
            p.setDisplayName(ColorUtils.color("&4&lADMIN &4&o") + p.getName());
            p.setPlayerListName(ColorUtils.color("&4&lADMIN &4&o") + p.getName());
            p.setCustomName(ColorUtils.color("&4&o") + p.getName());
        } else if (p.hasPermission("tag.modplus")) {
            p.setDisplayName(ColorUtils.color("&5&lMOD+ &5&o") + p.getName());
            p.setPlayerListName(ColorUtils.color("&5&lMOD+ &5&o") + p.getName());
            p.setCustomName(ColorUtils.color("&5&o") + p.getName());
        } else if (p.hasPermission("tag.mod")) {
            p.setDisplayName(ColorUtils.color("&5&lMOD &5&o") + p.getName());
            p.setPlayerListName(ColorUtils.color("&5&lMOD &5&o") + p.getName());
            p.setCustomName(ColorUtils.color("&5&o") + p.getName());
        } else {
            p.setDisplayName(ColorUtils.color("&7") + p.getName());
            p.setPlayerListName(ColorUtils.color("&7") + p.getName());
            p.setCustomName(ColorUtils.color("&7") + p.getName());
        }
    }
}
