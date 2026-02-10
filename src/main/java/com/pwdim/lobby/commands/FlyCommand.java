package com.pwdim.lobby.commands;

import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        MyUtils.commandConsoleChecker(sender, "lobby.fly");
        Player p = (Player) sender;

        if (args.length == 0){
            if (!p.isFlying()) {
                p.setAllowFlight(true);
                p.setFlying(true);
                sender.sendMessage(MyUtils.color("&aFly ativado!"));

            } else if (p.isFlying()){
                p.setAllowFlight(false);
                p.setFlying(false);
                sender.sendMessage(MyUtils.color("&cFly desativado!"));
            }
        }
        return true;
    }
}