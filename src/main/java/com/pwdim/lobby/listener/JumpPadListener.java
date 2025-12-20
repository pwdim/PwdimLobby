package com.pwdim.lobby.listener;


import com.pwdim.lobby.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.Vector;

public class JumpPadListener implements Listener {

    @EventHandler
    public void onTrampoline(PlayerMoveEvent event){
        Player p = event.getPlayer();
        Vector v = event.getPlayer().getLocation().getDirection();
        if(p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.SLIME_BLOCK){
            p.setVelocity(new Vector(1, 1, 0));
        }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onServerMOTD(ServerListPingEvent e) {
        ConfigurationSection section = Bukkit.getPluginManager().getPlugin("Lobby").getConfig().getConfigurationSection("server");
        StringBuilder sb = new StringBuilder();

        int totalLenght = 52;
        String motd = section.getString(".motd");

        e.setMotd(motd);

        e.setMaxPlayers(Integer.parseInt(section.getString(".max")));

    }
}
