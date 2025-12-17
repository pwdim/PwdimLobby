package com.pwdim.lobby.commands;

import com.pwdim.lobby.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ColorUtils.color("&cVocê precisa inserir uma mensagem"));
            return true;
        } else {
            String mensagem = String.join(" ", args);
            Bukkit.broadcastMessage(ColorUtils.color("&b&lBANMC &r" + mensagem));

            return true;
        }
    }

}
