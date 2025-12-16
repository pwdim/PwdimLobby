package com.pwdim.tutorial;

import com.pwdim.tutorial.commands.*;
import com.pwdim.tutorial.events.ChatEvent;
import com.pwdim.tutorial.listener.JoinMessageListener;
import com.pwdim.tutorial.listener.LobbyListener;
import com.pwdim.tutorial.listener.ScoreBoardListener;
import com.pwdim.tutorial.utils.ColorUtils;
import fr.mrmicky.fastboard.FastBoard;
import fr.mrmicky.fastboard.FastBoardBase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public final class TUTORIAL extends JavaPlugin {
    private final ArrayList<Player> staffVanished = new ArrayList<>();
    private final ArrayList<Player> staffVisualize = new ArrayList<>();
    private final ArrayList<UUID> buildMode = new ArrayList<>();
    private final Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
    int playerCount = onlinePlayers.size();
    String playerList = onlinePlayers.stream().
            map(Player::getName).
            collect(Collectors.joining(", "));
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private int updateTaskID = -1;

    public Map<UUID, FastBoard> getBoards() {
        return boards;
    }
    public void updateScore() {
        final String[] TITLES = {
                ColorUtils.color("&1&lPWDIM"),
                ColorUtils.color("&9&lP&1&lWDIM"),
                ColorUtils.color("&b&lP&9&lW&1&lDIM"),
                ColorUtils.color("&b&lPW&9&lD&1&lIM"),
                ColorUtils.color("&b&lPWD&9&lI&1&lM"),
                ColorUtils.color("&b&lPWD&9&lIM"),
                ColorUtils.color("&b&lPWDI&9&lM"),
                ColorUtils.color("&b&lPWDIM"),
                ColorUtils.color("&9&lP&b&lWDIM"),
                ColorUtils.color("&1&lP&9&lW&b&lDIM"),
                ColorUtils.color("&1&lPW&9&lD&b&lIM"),
                ColorUtils.color("&1&lPWD&9&lI&b&lM"),
                ColorUtils.color("&1&lPWDI&9&lM"),
        };
        updateTaskID = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            private int titleIndex = 0;

            @Override
            public void run() {
                titleIndex = (titleIndex + 1) % TITLES.length;
                String newTitle = TITLES[titleIndex];
                int players = Bukkit.getOnlinePlayers().size() - getVanishedPlayers().size();
                String onlineLine = ColorUtils.color("&fOnline: &b&o" + players);


                for (FastBoard board : boards.values()) {
                    board.updateTitle(newTitle);
                    board.updateLine(3, onlineLine);
                }
            }
        },
                0L, 5L) .getTaskId();
    }


    @Override
    public void onEnable() {
        saveDefaultConfig();

        String server = "#1";

        Bukkit.getLogger().info(ColorUtils.color("Plugin iniciado com sucesso!") );


        getServer().getPluginManager().registerEvents(new LobbyListener(this), this);
        Bukkit.getLogger().info(ColorUtils.color("LobbyListerner registrado") );
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        Bukkit.getLogger().info(ColorUtils.color("ChatEvent registrado") );
        getServer().getPluginManager().registerEvents(new ScoreBoardListener(this), this);
        Bukkit.getLogger().info(ColorUtils.color("ScoreBoardListerner registrado") );
        getServer().getPluginManager().registerEvents(new JoinMessageListener(this), this);
        Bukkit.getLogger().info(ColorUtils.color("JoinMessageListerner registrado") );
        //getServer().getPluginManager().registerEvents(new VanishListener(this), this);
        Bukkit.getLogger().info(ColorUtils.color("VanishListener registrado") );


        getCommand("broadcast").setExecutor(new BroadcastCommand());
        Bukkit.getLogger().info(ColorUtils.color("Comando /broadcast iniciado") );
        getCommand("tell").setExecutor(new TellCommand(this));
        Bukkit.getLogger().info(ColorUtils.color("Comando /tell iniciado") );
        getCommand("staffchat").setExecutor(new StaffChatCommand());
        Bukkit.getLogger().info(ColorUtils.color("Comando /staffchat iniciado") );
        getCommand("vanish").setExecutor(new VanishCommand(this));
        Bukkit.getLogger().info(ColorUtils.color("Comando /vanish iniciado") );
        getCommand("build").setExecutor(new BuildCommand(this));
        Bukkit.getLogger().info(ColorUtils.color("Comando /build iniciado") );
        getCommand("fly").setExecutor(new FlyCommand());
        Bukkit.getLogger().info(ColorUtils.color("Comando /fly iniciado") );
        getCommand("gm").setExecutor(new GameModeCommand());
        Bukkit.getLogger().info(ColorUtils.color("Comando /gm iniciado") );

        updateScore();


    }

    public ArrayList<Player> getVanishedPlayers() {
        return staffVanished;
    }
    public ArrayList<Player> getStaffVisualize() {return staffVisualize;}

    public ArrayList<UUID> getBuilders() {
        return buildMode;
    }
    public int getOnlinePlayers() {
        return playerCount;
    }




    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ColorUtils.color("Plugin desligado com sucesso!") );

        if (updateTaskID != -1) {
            getServer().getScheduler().cancelTask(updateTaskID);
        }
        boards.values().forEach(FastBoardBase::delete);
        boards.clear();
    }

    public void updateOnline() {
        updateTaskID = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {

                    @Override
                    public void run() {
                        String onlineLine = ColorUtils.color("&fOnline: &b&o" + Bukkit.getOnlinePlayers().size());


                        for (FastBoard board : boards.values()) {
                            board.updateLine(3, onlineLine);
                        }
                    }
                },
                0L, 5L).getTaskId();
    }
}
