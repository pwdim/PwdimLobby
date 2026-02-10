package com.pwdim.lobby.commands.staff;

import com.pwdim.lobby.utils.ConfigUtils;
import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        MyUtils.commandPermissionChecker(sender, "lobby.broadcast");
        String mensagem = String.join(" ", args);
        MyUtils.broadcast(mensagem);
        MyUtils.staffLog(sender, sender.getName() + " &7&oenviou um broadcast: ");
        MyUtils.staffLog(sender, sender.getName() + "&7&o: " + mensagem);

        return true;
    }

}
