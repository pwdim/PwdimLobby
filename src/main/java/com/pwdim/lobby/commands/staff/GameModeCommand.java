package com.pwdim.lobby.commands.staff;

import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        MyUtils.commandConsoleChecker(sender, "staff.gamemode");
        Player p = (Player) sender;

        if (args.length == 0){
            MyUtils.message(p, "&cUse: /gamemode <modo> <jogador>");
            return true;
        }

        if (args.length == 1){
            try {
                String gamemode = args[0];


                switch (gamemode){
                    case "survival":
                    case "0":
                        p.setGameMode(GameMode.SURVIVAL);
                        break;
                    case "creative":
                    case "criativo":
                    case "1":
                        p.setGameMode(GameMode.CREATIVE);
                        break;
                    case "adventure":
                    case "aventura":
                    case "2":
                        p.setGameMode(GameMode.ADVENTURE);
                        break;
                    case "spectator":
                    case "espectador":
                    case "3":
                        p.setGameMode(GameMode.SPECTATOR);
                        break;
                }
                MyUtils.message(p, "&aModo de jogo atualizado para " + MyUtils.gamemodeName(p));
                MyUtils.staffLog(p, p.getCustomName() + "&7&o entrou no gamemode " + MyUtils.gamemodeName(p));
                return true;
            } catch (NumberFormatException e) {
                MyUtils.message(p, "&cModo de jogo inválido.");
                return true;
            }
        }

        if (args.length == 2){
            try {
                String gamemode = args[0];
                String targetName = args[1];
                Player target = Bukkit.getPlayer(targetName);

                if (target != null) {
                    switch (gamemode) {
                        case "survival":
                        case "0":
                            target.setGameMode(GameMode.SURVIVAL);
                            break;
                        case "creative":
                        case "criativo":
                        case "1":
                            target.setGameMode(GameMode.CREATIVE);
                            break;
                        case "adventure":
                        case "aventura":
                        case "2":
                            target.setGameMode(GameMode.ADVENTURE);
                            break;
                        case "spectator":
                        case "espectador":
                        case "3":
                            target.setGameMode(GameMode.SPECTATOR);
                            break;
                    }
                    MyUtils.message(target, "&aModo de jogo atualizado para " + MyUtils.gamemodeName(target));
                    MyUtils.message(p, "&aModo de jogo de &7" + target.getCustomName() + " &aatualizado para " + MyUtils.gamemodeName(target));
                    MyUtils.staffLog(p, "&7&oModo de jogo de &b" + target.getCustomName() + " &7&oatualizado para " + MyUtils.gamemodeName(target) + " por " + p.getCustomName());
                }
                return true;
            } catch(NumberFormatException e){
                MyUtils.message(p, "&cModo de jogo e/ou jogador inválidos.");
                return true;
            }

        }
        return false;
    }
}
