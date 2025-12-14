package com.pwdim.tutorial;

import com.pwdim.tutorial.commands.*;
import com.pwdim.tutorial.events.ChatEvent;
import com.pwdim.tutorial.listener.LobbyListener;
import com.pwdim.tutorial.listener.ScoreBoardListener;
import com.pwdim.tutorial.listener.VanishListener;
import com.pwdim.tutorial.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public final class TUTORIAL extends JavaPlugin {
    private final ArrayList<UUID> staffVanished = new ArrayList<>();
    private final ArrayList<UUID> buildMode = new ArrayList<>();
    private final Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
    int playerCount = onlinePlayers.size();
    String playerList = onlinePlayers.stream().
            map(Player::getName).
            collect(Collectors.joining(", "));


    @Override
    public void onEnable() {
        saveDefaultConfig();

        String server = "#1";

        Bukkit.getLogger().info(ColorUtils.color("Plugin iniciado com sucesso!") );


        getServer().getPluginManager().registerEvents(new LobbyListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        getServer().getPluginManager().registerEvents(new ScoreBoardListener(this), this);


        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("tell").setExecutor(new TellCommand(this));
        getCommand("staffchat").setExecutor(new StaffChatCommand());
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("build").setExecutor(new BuildCommand(this));


    }

    public ArrayList<UUID> getVanishedPlayers() {
        return staffVanished;
    }

    public ArrayList<UUID> getBuilders() {
        return buildMode;
    }
    public String getOnlinePlayers() {
        return playerList;
    }



    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ColorUtils.color("Plugin desligado com sucesso!") );
    }
}
