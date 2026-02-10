package com.pwdim.lobby;

import com.pwdim.lobby.commands.*;
import com.pwdim.lobby.commands.MotdCommand;
import com.pwdim.lobby.commands.staff.*;
import com.pwdim.lobby.events.ChatEvent;
import com.pwdim.lobby.itens.*;
import com.pwdim.lobby.listener.*;
import com.pwdim.lobby.manager.PermissionManager;
import com.pwdim.lobby.utils.MyUtils;
import com.pwdim.lobby.utils.HologramUtils;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import fr.mrmicky.fastboard.FastBoard;
import fr.mrmicky.fastboard.FastBoardBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public final class LOBBY extends JavaPlugin {

// Jogadores no vanish e no visualize
    private final ArrayList<UUID> staffVanished = new ArrayList<>();
    private final ArrayList<UUID> staffVisualize = new ArrayList<>();

// Jogadores no staffchat
    private final ArrayList<UUID> staffChatSync = new ArrayList<>();
    private final ArrayList<UUID> staffChatOn = new ArrayList<>();

// Jogadores no modo build
    private final ArrayList<UUID> buildMode = new ArrayList<>();

// Update de jogadores online
    int playerCount = Bukkit.getOnlinePlayers().size();
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private int updateTaskID = -1;

// ###################
// Informações de Jogadores
// ######################
    private File playersFile;
    private YamlConfiguration playersConfig;
    //Tags
    public static HashMap<UUID, String> playerTag = new HashMap<>();
    // Ranks
    public static HashMap<UUID, String> playerRank = new HashMap<>();
    
// Sistema de /tell e /r
    public static Map<UUID, UUID> reciverList = new HashMap<>();
    public LOBBY() throws SQLException {
    }


// ###################
// Sistema de Informações de Jogadores
// ######################

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

    // Sistema de spawns por mundo
    private File spawnsFile;
    private YamlConfiguration spawnsConfig;
    public static HashMap<World, Location> spawnLocation = new HashMap<>();
    public static HashMap<World, Location> spawnVipLocation = new HashMap<>();

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


    public void savePlayersData() {
        playersConfig.set("users", null);

        for (UUID uuid : playerTag.keySet()) {
            playersConfig.set("users." + uuid.toString() + ".profile.tag", playerTag.get(uuid));
            playersConfig.set("users." + uuid.toString() + ".profile.rank", playerRank.get(uuid));
        }

        for (UUID uuid : playerRank.keySet()) {
            playersConfig.set("users." + uuid.toString() + ".profile.rank", playerRank.get(uuid));
        }


        try {
            playersConfig.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void loadPlayersData() {
        ConfigurationSection section = playersConfig.getConfigurationSection("users");
        if (section == null) return;

        int contador = 0;

        for (String uuidString : section.getKeys(false)) {
            UUID uuid = UUID.fromString(uuidString);


            String tag = section.getString(uuidString + ".profile.tag");
            String rank = section.getString(uuidString + ".profile.rank");

            if (tag != null) {
                playerTag.put(uuid, tag);
            }

            if (rank != null) {
                playerRank.put(uuid, rank);
                contador++;
            }
        }
    }

    public void saveSpawnsData() {
        spawnsConfig.set("worlds", null);

        for (World world : spawnLocation.keySet()) {
            spawnsConfig.set("worlds." + world.getName(), spawnLocation.get(world));
            spawnsConfig.set("worlds." + world.getName() + ".vip", spawnVipLocation.get(world));
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
            Location locVip = (Location) section.get(worldName + ".vip");

            spawnLocation.put(world, loc);
            spawnVipLocation.put(world, loc);
            contador++;
        }
        Bukkit.getConsoleSender().sendMessage("§aCarregando informacoes de §b§l" + contador +" §aspawns");
    }


// ###################
// Sistema de Scoreboard
// ######################

    public Map<UUID, FastBoard> getBoards() {
        return boards;
    }

    public void updateScore() {
        final String[] TITLES = {
                MyUtils.color("&1&lPWDIM"),
                MyUtils.color("&9&lP&1&lWDIM"),
                MyUtils.color("&b&lP&9&lW&1&lDIM"),
                MyUtils.color("&b&lPW&9&lD&1&lIM"),
                MyUtils.color("&b&lPWD&9&lI&1&lM"),
                MyUtils.color("&b&lPWD&9&lIM"),
                MyUtils.color("&b&lPWDI&9&lM"),
                MyUtils.color("&b&lPWDIM"),
                MyUtils.color("&9&lPWDIM"),
                MyUtils.color("&1&lPWDIM"),
                MyUtils.color("&b&lPWDIM"),
                MyUtils.color("&9&lP&b&lWDIM"),
                MyUtils.color("&1&lP&9&lW&b&lDIM"),
                MyUtils.color("&1&lPW&9&lD&b&lIM"),
                MyUtils.color("&1&lPWD&9&lI&b&lM"),
                MyUtils.color("&1&lPWDI&9&lM"),
        };

        updateTaskID = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
                    private int titleIndex = 0;

                    @Override
                    public void run() {
                        titleIndex = (titleIndex + 1) % TITLES.length;
                        String newTitle = TITLES[titleIndex];
                        String onlineLine = MyUtils.color("&fOnline: &b&o" + getOnlinePlayers());


                        for (FastBoard board : boards.values()) {
                            board.updateTitle(newTitle);
                            board.updateLine(3, onlineLine);
                        }
                    }
                },
                0L, 5L) .getTaskId();

    }


// ###################
// LIGANDO O SERVIDOR
// ######################


    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupPlayersConfig();
        loadPlayersData();

        setupSpawnConfig();
        loadSpawnsData();

        Bukkit.getLogger().info(("§a§oPlugin iniciado com sucesso!"));

        getServer().getPluginManager().registerEvents(new LobbyListener(this), this);
        getServer().getPluginManager().registerEvents(new HologramUtils(), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(this), this);
        getServer().getPluginManager().registerEvents(new ScoreBoardListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinMessageListener(this), this);
        getServer().getPluginManager().registerEvents(new JumpPadListener(), this);
        getServer().getPluginManager().registerEvents(new NoPermListener(this), this);
        getServer().getPluginManager().registerEvents(new ChestItem(), this);
        getServer().getPluginManager().registerEvents(new CompassItem(), this);
        getServer().getPluginManager().registerEvents(new ProfileItem(this), this);
        getServer().getPluginManager().registerEvents(new StarItem(), this);
        getServer().getPluginManager().registerEvents(new PermissionManager(this), this);
        getServer().getPluginManager().registerEvents(new KangarooItem(), this);

        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("tell").setExecutor(new TellCommand(this));
        getCommand("r").setExecutor(new RCommand());
        getCommand("staffchat").setExecutor(new StaffChatCommand(this));
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("build").setExecutor(new BuildCommand(this));
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("gm").setExecutor(new GameModeCommand());
        getCommand("tag").setExecutor(new TagCommand(this));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("lobbyreload").setExecutor(new ReloadCommand(this));
        getCommand("motd").setExecutor(new MotdCommand());
        getCommand("group").setExecutor(new GroupCommand(this));
        getCommand("setgroup").setExecutor(new SetGroupCommand(this));
        getCommand("account").setExecutor(new AccountCommand(this));
        getCommand("hologram").setExecutor(new HologramCommand());
        getCommand("speed").setExecutor(new SpeedCommand());
        getCommand("togglechat").setExecutor(new ChatToggleCommand());





        updateScore();

    }


// ###################
// TODOS OS GET'S
// ######################

    // gets de mundos
    public Location getWorldSpawn(World world) {
        if (spawnLocation.containsKey(world)) {
            return spawnLocation.get(world);
        }
        return world.getSpawnLocation();
    }

    public Location getWorldVipSpawn(World world) {
        if (spawnVipLocation.containsKey(world)) {
            return spawnVipLocation.get(world);
        }
        return world.getSpawnLocation();
    }

// Permissoes de Staff
    public ArrayList<UUID> getVanishedPlayers() { return staffVanished; }
    public ArrayList<UUID> getStaffVisualize() { return staffVisualize; }
    public ArrayList<UUID> getBuilders() { return buildMode; }

// Jogadores Online
    public int getOnlinePlayers() {
        long vanishedOnline = getVanishedPlayers().stream()
                .map(Bukkit::getPlayer)
                .filter(p -> p != null && p.isOnline())
                .count();

        return (int) (Bukkit.getOnlinePlayers().size() - vanishedOnline);
    }
// Rank do jogador
    public String getPlayerRank(UUID uuid) {
        if (playerRank.containsKey(uuid)) {
            return playerRank.get(uuid);
        }
        return getConfig().getString("default-rank", "membro");
    };
 // Mudar rank do jogador
    public void setPlayerRank(UUID uuid, String rank) {
        playerRank.put(uuid, rank);
    };
// Mudar permissao de um rank
    public void addRankPermission(String rank, String perm) {
        ConfigurationSection section = Bukkit.getServer().getPluginManager().getPlugin("Lobby").getConfig().getConfigurationSection(".ranks");

        String path = "." +rank;
        List<String> rankPerms = section.getStringList(path);

        if (!rankPerms.contains(perm)) {
            rankPerms.add(perm);
            section.set(path, rankPerms);
            saveConfig();
            savePlayersData();
            reloadConfig();
            loadPlayersData();
        }
    }

    public void removeRankPermission(String rank, String perm) {
        ConfigurationSection section = Bukkit.getServer().getPluginManager().getPlugin("Lobby").getConfig().getConfigurationSection(".ranks");
        String path = "."+rank;

        List<String> rankPerms = section.getStringList(rank);

        if (rankPerms.contains(perm)) {
            rankPerms.remove(perm);
            section.set(path, rankPerms);
            saveConfig();
            savePlayersData();
            reloadConfig();
            loadPlayersData();
        }
    }
// Ver permissoes do jogador
public List<String> listRankPermissions(String rank) {
    ConfigurationSection section = Bukkit.getServer().getPluginManager().getPlugin("Lobby").getConfig().getConfigurationSection(".ranks");
    String path = "."+rank;

    List<String> rankPerms = section.getStringList(rank);

    return rankPerms;
}

// Display do Rank
    public String getRankDisplay(OfflinePlayer p){
        UUID uuid = p.getUniqueId();
        ConfigurationSection tagsSection = Bukkit.getPluginManager().getPlugin("Lobby").getConfig().getConfigurationSection("tags");

        String rankName = getPlayerRank(uuid);

        return MyUtils.color(tagsSection.getString("."+rankName+".type", "&0&l" + rankName.toUpperCase()));
    }
    public ProtocolVersion getPlayerVersion(OfflinePlayer p) {
        return Via.getAPI().getPlayerProtocolVersion(p);
    }
    public boolean isPremiumPlayer(OfflinePlayer p) {
        UUID uuid = p.getUniqueId();

        try {

            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();

            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
// Tag do jogador
    public String getPlayerTag(OfflinePlayer p) {
        UUID uuid = p.getUniqueId();
        if (playerTag.containsKey(uuid)) {
            return playerTag.get(uuid);
        }
        return getConfig().getString("default-tag", "membro");
    };
// Prefix do jogador
    public String getPlayerPrefix(OfflinePlayer p) {
        String tag = getPlayerTag(p);
        String prefix = getConfig().getString("tags."+tag+".prefix", "");
        return MyUtils.color(prefix);
    }
// Cor da Tag do jogador
    public String getPlayerColor(OfflinePlayer p) {
        String tag = getPlayerTag(p);
        String prefix = getConfig().getString("tags."+tag+".name-color", "");

        return MyUtils.color(prefix);
    }
// Setar nametag
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


// Conferir todos os comandos do servidor

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
 // Conferir mensagem de sem Permissão
    public String noPermMessage() {
        ConfigurationSection section = Bukkit.getPluginManager().getPlugin("Lobby").getConfig().getConfigurationSection(".server");

        return MyUtils.color(section.getString(".no-perm.message", "&cSem permissão!"));
    }

// Conferir um comando
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
        playerTag.clear();
        playerRank.clear();
        spawnLocation.clear();
        spawnVipLocation.clear();
    }


    public void updateOnline() {
        updateTaskID = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {

                    @Override
                    public void run() {
                        String onlineLine = MyUtils.color("&fOnline: &b&o" + getOnlinePlayers());

                        for (FastBoard board : boards.values()) {
                            board.updateLine(3, onlineLine);
                        }
                    }
                },
                0L, 0L).getTaskId();

    }

    public ArrayList<UUID> getStaffChatSync() {
        return staffChatSync;
    }

    public ArrayList<UUID> getStaffChatOn() {
        return staffChatOn;
    }
}