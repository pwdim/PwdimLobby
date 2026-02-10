package com.pwdim.lobby.commands.staff;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final LOBBY plugin;

    public ReloadCommand(LOBBY plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        MyUtils.commandPermissionChecker(sender, "lobby.reload");

        plugin.reloadConfig();
        MyUtils.message(sender, "&aConfigurações do plugin recarregadas.");


        return true;
    }
}
