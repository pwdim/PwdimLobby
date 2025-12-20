package com.pwdim.lobby;

import com.pwdim.lobby.commands.*;
import com.pwdim.lobby.events.ChatEvent;
import com.pwdim.lobby.listener.*;
import com.pwdim.lobby.utils.ColorUtils;
import fr.mrmicky.fastboard.FastBoard;
import fr.mrmicky.fastboard.FastBoardBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public final class LOBBY extends JavaPlugin {


    // Jogadores no vanish e no visualize
    private final ArrayList<Player> staffVanished = new ArrayList<>();
    private final ArrayList<Player> staffVisualize = new ArrayList<>();

    // Jogadores no modo build
    private final ArrayList<UUID> buildMode = new ArrayList<>();

    // Update de jogadores online
    private final Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
    int playerCount = onlinePlayers.size();
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private int updateTaskID = -1;

    // Sistema de tags
    private File playersFile;
    private YamlConfiguration playersConfig;
    public static HashMap<UUID, String> playerTag = new HashMap<>();

    // Sistema de /tell e /r
    public static Map<Player, Player> reciverList = new HashMap<>();



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
            playersConfig.set("users." + uuid.toString() + ".tag", playerTag.get(uuid));
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

            String tag = section.getString(uuidString + ".tag");

            if (tag != null) {
                playerTag.put(uuid, tag);
                contador++;
            }

        }
        Bukkit.getConsoleSender().sendMessage("§aCarregando informações de §b§l" + contador +" §ajogadores");
    }


    // Sistema de spawns por mundo
    private File spawnsFile;
    private YamlConfiguration spawnsConfig;
    public static HashMap<World, Location> spawnLocation = new HashMap<>();

    public void setupSpawnConfig() {
        if (!getDataFolder().exists()) getDataFolder().mkdir();

        spawnsFile = new File(getDataFolder(), "spawns.yml");

        if (!spawnsFile.exists()) {
            try {
                spawnsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        spawnsConfig = YamlConfiguration.loadConfiguration(spawnsFile);
    }

    public void saveSpawnsData() {
        spawnsConfig.set("worlds", null);

        for (World world : spawnLocation.keySet()) {
            spawnsConfig.set("worlds." + world.getName(), spawnLocation.get(world));
        }
        try {
            spawnsConfig.save(spawnsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadSpawnsData() {
        ConfigurationSection section = spawnsConfig.getConfigurationSection("worlds");
        int contador = 0;

        if (section == null) return;

        for (String worldName : section.getKeys(false) ) {
            World world = Bukkit.getWorld(worldName);

            if (world == null) continue;

            Location loc = (Location) section.get(worldName);

            spawnLocation.put(world, loc);
            contador++;
        }
        Bukkit.getConsoleSender().sendMessage("§aCarregando informacoes de §b§l" + contador +" §aspawns");
    }

    // Sistema de scoreboard
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

        setupSpawnConfig();
        loadSpawnsData();



        Bukkit.getLogger().info(("§a§oPlugin iniciado com sucesso!"));


        getServer().getPluginManager().registerEvents(new LobbyListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        getServer().getPluginManager().registerEvents(new ScoreBoardListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinMessageListener(this), this);
        getServer().getPluginManager().registerEvents(new JumpPadListener(), this);
        getServer().getPluginManager().registerEvents(new NoPermListener(this), this);


        getCommand("broadcast").setExecutor(new BroadcastCommand());
        Bukkit.getLogger().info(("§bComando /broadcast iniciado") );
        getCommand("tell").setExecutor(new TellCommand(this));
        getCommand("r").setExecutor(new RCommand());
        Bukkit.getLogger().info(("§bComando /tell iniciado") );
        getCommand("staffchat").setExecutor(new StaffChatCommand(this));
        Bukkit.getLogger().info(("§bComando /staffchat iniciado") );
        getCommand("vanish").setExecutor(new VanishCommand(this));
        Bukkit.getLogger().info(("§bComando /vanish iniciado") );
        getCommand("build").setExecutor(new BuildCommand(this));
        Bukkit.getLogger().info(("§bComando /build iniciado") );
        getCommand("fly").setExecutor(new FlyCommand());
        Bukkit.getLogger().info(("§bComando /fly iniciado") );
        getCommand("gm").setExecutor(new GameModeCommand());
        Bukkit.getLogger().info(("§bComando /gm iniciado") );

        getCommand("tag").setExecutor(new TagCommand(this));
        Bukkit.getLogger().info(("§bComando /tag iniciado") );

        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        Bukkit.getLogger().info(("§bComando /setspawn iniciado") );

        getCommand("spawn").setExecutor(new SpawnCommand(this));
        Bukkit.getLogger().info(("§bComando /spawn iniciado") );

        getCommand("lobbyreload").setExecutor(new ReloadCommand(this));
        Bukkit.getLogger().info(("§bComando /lobbyreload iniciado") );

        getCommand("motd").setExecutor(new MotdCommand());
        Bukkit.getLogger().info(("§bComando /motd iniciado") );

        updateScore();


    }

    // ###################
    //  TODOS OS GET'S
    // ######################

    // gets de staff
    public ArrayList<Player> getVanishedPlayers() {
        return staffVanished;
    }
    public ArrayList<Player> getStaffVisualize() {return staffVisualize;}
    public ArrayList<UUID> getBuilders() {
        return buildMode;
    }

    // gets de players
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

        ConfigurationSection section = Bukkit.getPluginManager().getPlugin("Lobby").getConfig().getConfigurationSection("tags");
        String priority = section.getString(getPlayerTag(player) + ".priority");

        String teamName = priority + player.getName();

        if (teamName.length() > 16) {
            teamName = teamName.substring(0, 16);
        }

        for (Player target : Bukkit.getOnlinePlayers()) {
            Scoreboard board = target.getScoreboard();
            Team team = board.getTeam(teamName);


            if (team == null) {
                team = board.registerNewTeam(teamName);
            }

            for (Team teamOld : board.getTeams()) {
                if (teamOld.hasEntry(player.getName())) {
                    teamOld.removeEntry(player.getName());
                }
            }

            team.setPrefix(prefix);
            team.addEntry(player.getName());
        }
    }


    // gets de mundos
    public Location getWorldSpawn(World world) {
        if (spawnLocation.containsKey(world)) {
            return spawnLocation.get(world);
        }
        return world.getSpawnLocation();
    }

    // gets todos os comandos
    public Collection<Command> getAllCommands() {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer());


            return commandMap.getCommands();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Command getAnyCommand(String name) {
        try {
            java.lang.reflect.Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            org.bukkit.command.CommandMap commandMap = (org.bukkit.command.CommandMap) field.get(Bukkit.getServer());

            return commandMap.getCommand(name);
        } catch (Exception e) {
            return null;
        }
    }

    // gets do discord7



    @Override
    public void onDisable() {
        savePlayersData();
        saveSpawnsData();

        Bukkit.getLogger().info(("§4Plugin desligado com sucesso!") );

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
