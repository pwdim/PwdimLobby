package com.pwdim.lobby.commands;

import com.pwdim.lobby.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;

import static com.pwdim.lobby.LOBBY.reciverList;

public class RCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (reciverList.containsValue(p.getPlayer()) || reciverList.containsKey(p.getPlayer()) ) {
                Player reciver = reciverList.get(sender);

                if (Bukkit.getOnlinePlayers().contains(reciver)) {
                    String[] newMsg = Arrays.copyOfRange(args, 0, args.length);
                    String mensagem = String.join(" ", newMsg);

                    String senderDisplay = p.getCustomName();
                    sender.sendMessage(ColorUtils.color("&8["+senderDisplay + "&8] &e➢➢ &8["+ reciver.getCustomName()+"&8] &e" + mensagem));
                    reciver.sendMessage(ColorUtils.color("&8["+senderDisplay + "&8] &e➢➢ &8["+ reciver.getCustomName()+"&8] &e" + mensagem));
                    reciverList.put(reciver, p.getPlayer());
                    reciverList.put(p.getPlayer(), reciver);
                } else {
                    reciverList.put(reciver, p.getPlayer());
                    reciverList.put(p.getPlayer(), reciver);
                    sender.sendMessage(ColorUtils.color("&cVocê não tem ninguem para responder!"));
                }

            } else {
                sender.sendMessage(ColorUtils.color("&cVocê não tem ninguem para responder!"));
            }
        } else {
            sender.sendMessage(ColorUtils.color("Você precisa ser um jogador para executar esse comando!"));
        }
        return true;

    }
}
