package com.pwdim.tutorial.commands;

import com.pwdim.tutorial.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;

public class TellCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String r = args[0];
        Player reciver = Bukkit.getPlayer(r);
        String senderDisplay;
        String[] newMsg = Arrays.copyOfRange(args, 1, args.length);

        String mensagem = String.join(" ", newMsg);
        if(args.length < 1) {
            sender.sendMessage(ColorUtils.color("&cERROR: /tell <nick> <mensagem>"));
            return true;
        }
        if (reciver == null) {
            sender.sendMessage(ColorUtils.color("&cInsira um nick válido"));
            return true;
        }

        if (mensagem.isEmpty()) {
            sender.sendMessage(ColorUtils.color("&cInsira uma mensagem"));
            return true;
        } else {
            Player p = (Player) sender;

            senderDisplay = p.getCustomName();
            sender.sendMessage(ColorUtils.color("&8["+senderDisplay + "&8] &e➢➢ &8["+ reciver.getCustomName()+"&8] &e" + mensagem));
            reciver.sendMessage(ColorUtils.color("&8["+senderDisplay + "&8] &e➢➢ &8["+ reciver.getCustomName()+"&8] &e" + mensagem));
        }

        return true;
    }
    @EventHandler
    public static void playerTell(PlayerCommandPreprocessEvent e) {
        String msg = e.getMessage();

        if (msg.toLowerCase().startsWith("/tell")) {
            e.setCancelled(true);
        }
    }
}
