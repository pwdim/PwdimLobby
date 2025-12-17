package com.pwdim.lobby.commands;

import com.pwdim.lobby.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Voce precisa ser um jogador para executar esse comando!");


            return true;
        } else {

            if (sender.hasPermission("lobby.fly")&& !((Player) sender).isFlying()) {
                ((Player) sender).setAllowFlight(true);
                ((Player) sender).setFlying(true);
                sender.sendMessage(ColorUtils.color("&aFly ativado!"));

            } else if (sender.hasPermission("lobby.fly") && ((Player) sender).isFlying()){
                ((Player) sender).setAllowFlight(false);
                ((Player) sender).setFlying(false);
                sender.sendMessage(ColorUtils.color("&cFly desativado!"));
            } else if(sender.hasPermission("lobby.fly") && args != null) {
                String flyspeed = args[0];
                ((Player) sender).setFlySpeed(Float.parseFloat(flyspeed));
                sender.sendMessage(ColorUtils.color("&aVelocidade de voo definida como "+flyspeed));
            } else if (!sender.hasPermission("lobby.fly")) {
                ((Player) sender).setAllowFlight(false);
                ((Player) sender).setFlying(false);
                sender.sendMessage(ColorUtils.color("&cVocê não possui permissão para executar esse comando!"));
            }

        }

        return true;
    }
}