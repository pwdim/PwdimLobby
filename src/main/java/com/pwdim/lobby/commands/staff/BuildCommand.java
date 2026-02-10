package com.pwdim.lobby.commands.staff;

import com.pwdim.lobby.LOBBY;
import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class BuildCommand implements CommandExecutor {

    private final LOBBY plugin;

    public BuildCommand(LOBBY plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<UUID> buildersList = plugin.getBuilders();

        Player builder = (Player) sender;

        MyUtils.commandConsoleChecker(sender, "lobby.build");

        if (buildersList.contains(builder.getUniqueId())) {
            buildersList.remove(builder.getUniqueId());
            builder.sendMessage(MyUtils.color("&cModo build desativado!"));
            MyUtils.staffLog(builder, builder.getCustomName() + "&7&o desativou o modo build");
        } else {
            buildersList.add(builder.getUniqueId());
            builder.sendMessage(MyUtils.color("&aModo build ativado!"));
            MyUtils.staffLog(builder, builder.getCustomName() + "&7&o ativou o modo build");
        }
        return true;


    }
}
