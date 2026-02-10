package com.pwdim.lobby.commands.staff;

import com.pwdim.lobby.utils.MyUtils;
import com.pwdim.lobby.utils.HologramUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HologramCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            Location loc = p.getLocation();

            if (args.length == 0){
                p.sendMessage(MyUtils.color("&cUse: /hologram texto"));
            }

            if (args.length > 0) {
                String msg = String.join(" ", args);

                HologramUtils.createHologram(loc, msg);
                p.sendMessage(MyUtils.color("&bHolograma criado com sucesso!"));
            }


        } else {
            sender.sendMessage(MyUtils.color("&cVocê precisa ser um jogador para usar esse comando!"));
        }
        return true;
    }
}
