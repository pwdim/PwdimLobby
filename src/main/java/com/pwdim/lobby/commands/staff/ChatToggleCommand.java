package com.pwdim.lobby.commands.staff;

import com.pwdim.lobby.events.ChatEvent;
import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatToggleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MyUtils.commandConsoleChecker(sender, "lobby.chat.toggle");
        Player p = (Player) sender;

        if (args.length == 0){
            MyUtils.message(p, "&cUse: /togglechat <on, off>");
            return true;
        }
        String toggle = args[0];

        switch (toggle){
            case "on":
                if (ChatEvent.getToggledChat()){
                    MyUtils.message(p, "&eO chat já está ativado.");
                } else {
                    MyUtils.message(p, "&aO chat foi ativado.");
                    ChatEvent.setToggledChat(true);
                }
                break;
            case "off":
                if (ChatEvent.getToggledChat()){
                    MyUtils.message(p, "&cO chat foi desativado.");
                    ChatEvent.setToggledChat(false);
                } else {
                    MyUtils.message(p, "&eO chat já está desativado.");
                }

                break;
            default:
                MyUtils.message(p, "&cUse: /togglechat <on, off>");
                break;
        }



        return true;
    }
}

