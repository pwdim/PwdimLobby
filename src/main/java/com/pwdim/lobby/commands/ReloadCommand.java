package com.pwdim.lobby.commands;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final LOBBY plugin;

    public ReloadCommand(LOBBY plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("lobby.reload")) {
            plugin.reloadConfig();

            sender.sendMessage(ColorUtils.color("&aConfigurações do lobby recarregadas"));


        } else {
            sender.sendMessage(ColorUtils.color("&cVocê não tem permissão para executar esse comando"));
        }

        return true;
    }
}
