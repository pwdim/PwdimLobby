package com.pwdim.lobby.listener;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
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
            FileConfiguration config = Bukkit.getPluginManager().getPlugin("Lobby").getConfig();
            String msg = config.getString("server.no-cmd-message", "&cComando não encontrado.");

            p.sendMessage(ColorUtils.color(msg));
        }


        if (cmd.getPermission() == null || cmd.getPermission().isEmpty()) {
            return;
        }

        if (!p.hasPermission(cmd.getPermission())) {
            e.setCancelled(true);

            FileConfiguration config = Bukkit.getPluginManager().getPlugin("Lobby").getConfig();
            String msg = config.getString("server.no-perm-message", "&cVocê não possui permissão para executar esse comando!");

            p.sendMessage(ColorUtils.color(msg));
        }
    }
}
