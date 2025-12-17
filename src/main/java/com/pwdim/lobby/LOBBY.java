package com.pwdim.lobby;

import com.pwdim.lobby.commands.*;
import com.pwdim.lobby.events.ChatEvent;
import com.pwdim.lobby.listener.JoinMessageListener;
import com.pwdim.lobby.listener.LobbyListener;
import com.pwdim.lobby.listener.ScoreBoardListener;
import com.pwdim.lobby.utils.ColorUtils;
import fr.mrmicky.fastboard.FastBoard;
import fr.mrmicky.fastboard.FastBoardBase;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class LOBBY extends JavaPlugin {
    private final ArrayList<Player> staffVanished = new ArrayList<>();
    private final ArrayList<Player> staffVisualize = new ArrayList<>();
    private final ArrayList<UUID> buildMode = new ArrayList<>();
    private final Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
    int playerCount = onlinePlayers.size();
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private int updateTaskID = -1;
    private File playersFile;
    private YamlConfiguration playersConfig;

    public static HashMap<UUID, String> playerTag = new HashMap<>();

    public void setupPlayersConfig() {
        if (!getDataFolder().exists()) getDataFolder().mkdir();

        playersFile = new File(getDataFolder(), "players.yml");

        if (!playersFile.exists()) {
            try {
                playersFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        playersConfig = YamlConfiguration.loadConfiguration(playersFile);
    }

    public void savePlayersData() {
        playersConfig.set("users", null);

        for (UUID uuid : playerTag.keySet()) {
            playersConfig.set("users." + uuid.toString(), playerTag.get(uuid));
        }
        try {
            playersConfig.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadPlayersData() {
        ConfigurationSection section = playersConfig.getConfigurationSection("users");
        int contador = 0;

        if (section == null) return;

        for (String uuidString : section.getKeys(false) ) {
            UUID uuid = UUID.fromString(uuidString);

            String tag = section.getString(uuidString);

            playerTag.put(uuid, tag);
            contador++;
        }
        Bukkit.getConsoleSender().sendMessage("§aCarregando informações de §b§l" + contador +" §ajogadores");
    }

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
                String onlineLine = ColorUtils.color("&fOnline: &b&o" + Bukkit.getOnlinePlayers().size());


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
        setupPlayersConfig();
        loadPlayersData();


        Bukkit.getLogger().info(ColorUtils.color("§aPlugin iniciado com sucesso!") );


        getServer().getPluginManager().registerEvents(new LobbyListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        getServer().getPluginManager().registerEvents(new ScoreBoardListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinMessageListener(this), this);
        //getServer().getPluginManager().registerEvents(new VanishListener(this), this);


        getCommand("broadcast").setExecutor(new BroadcastCommand());
        Bukkit.getLogger().info(ColorUtils.color("§bComando /broadcast iniciado") );
        getCommand("tell").setExecutor(new TellCommand(this));
        Bukkit.getLogger().info(ColorUtils.color("§bComando /tell iniciado") );
        getCommand("staffchat").setExecutor(new StaffChatCommand());
        Bukkit.getLogger().info(ColorUtils.color("§bComando /staffchat iniciado") );
        getCommand("vanish").setExecutor(new VanishCommand(this));
        Bukkit.getLogger().info(ColorUtils.color("§bComando /vanish iniciado") );
        getCommand("build").setExecutor(new BuildCommand(this));
        Bukkit.getLogger().info(ColorUtils.color("§bComando /build iniciado") );
        getCommand("fly").setExecutor(new FlyCommand());
        Bukkit.getLogger().info(ColorUtils.color("§bComando /fly iniciado") );
        getCommand("gm").setExecutor(new GameModeCommand());
        Bukkit.getLogger().info(ColorUtils.color("§bComando /gm iniciado") );

        getCommand("tag").setExecutor(new TagCommand(this));
        Bukkit.getLogger().info(ColorUtils.color("§bComando /tag iniciado") );

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
    public String getPlayerTag(Player player) {
        UUID uuid = player.getUniqueId();
        if (playerTag.containsKey(uuid)) {
            return playerTag.get(uuid);
        }
        return getConfig().getString("default-tag", "membro");
    };

    public String getPlayerPrefix(Player player) {

        String tag = getPlayerTag(player);

        String prefix = getConfig().getString("tags."+tag+".prefix", "");

        return ColorUtils.color(prefix);

    }
    public String getPlayerColor(Player player) {

        String tag = getPlayerTag(player);

        String prefix = getConfig().getString("tags."+tag+".name-color", "");

        return ColorUtils.color(prefix);

    }
    public void setNameTag(Player player) {
        String prefix = getPlayerPrefix(player);

        Scoreboard board = player.getScoreboard();

        String teamName = player.getName();
        Team team = board.getTeam(teamName);

        if (team == null) {
            team = board.registerNewTeam(teamName);
        }

        team.setPrefix(prefix);
        team.addEntry(player.getName());
    }




    @Override
    public void onDisable() {
        savePlayersData();
        Bukkit.getLogger().info(ColorUtils.color("§4Plugin desligado com sucesso!") );

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
