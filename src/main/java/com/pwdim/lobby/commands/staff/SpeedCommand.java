package com.pwdim.lobby.commands.staff;

import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        MyUtils.commandConsoleChecker(sender, "lobby.speed");


        Player p = (Player) sender;

        if (args.length < 2){
            MyUtils.message(p, "&cUse: /speed <type> <0-1>");
            return true;
        }
        if (args.length == 2){
            try {
                String type = args[0];
                float speed = Float.parseFloat(args[1]);

                if (speed >= 0 && speed <= 1){
                    switch (type) {
                        case "flying":
                        case "fly":
                            MyUtils.message(p, "&aVelocidade de voo definida como &b" + speed);
                            p.setFlySpeed(speed);
                            break;
                        case "walking":
                        case "walk":
                            MyUtils.message(p, "&aVelocidade definida como &b" + speed);
                            p.setWalkSpeed(speed);
                            break;
                        default:
                            MyUtils.message(p, "&cTipo inválido: Insira fly ou walk.");
                            break;
                    }
                } else {
                    MyUtils.message(p, "&cNúmero inválido: Insira um número entre 0 e 1.");
                }
            } catch (NumberFormatException e) {
                MyUtils.message(p, "&cNúmero inválido: Insira um número entre 0 e 1.");
            }
            return true;
        }
        return false;
    }
}
