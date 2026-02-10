package com.pwdim.lobby.listener;


import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class NoPermListener implements Listener {

    private final LOBBY plugin;

    public NoPermListener(LOBBY plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();


        String message = e.getMessage();
        String[] split = message.split(" ");
        String commandName = split[0].toLowerCase().substring(1);


        Command cmd = plugin.getAnyCommand(commandName);


        if (cmd == null) {
            e.setCancelled(true);


            p.sendMessage(ConfigUtils.noCommandMessage());
        }


        assert cmd != null;
        if (cmd.getPermission() == null || cmd.getPermission().isEmpty()) {
            return;
        }

        if (!p.hasPermission(cmd.getPermission())) {
            e.setCancelled(true);

            p.sendMessage(ConfigUtils.noPermMessage());
        }
    }

} 