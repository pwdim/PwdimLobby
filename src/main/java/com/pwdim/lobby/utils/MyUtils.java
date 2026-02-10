package com.pwdim.lobby.utils;

import com.pwdim.lobby.LOBBY;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public class MyUtils {

    private static LOBBY plugin;

    public MyUtils(LOBBY plugin){
        this.plugin = plugin;
    }

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String color(String message, Object... args) {
        String formattedMessage = String.format(message, args);
        return ChatColor.translateAlternateColorCodes('&', formattedMessage);
    }

    public static void log(String message){
        Configuration configuration = Bukkit.getPluginManager().getPlugin("Lobby").getConfig();
        String prefix = configuration.getString("prefix", "&r");

        Bukkit.getConsoleSender().sendMessage(color(prefix + message));
    }

    public static void commandConsoleChecker(CommandSender sender, String permission){

        if (!(sender instanceof Player)){
            sender.sendMessage(ConfigUtils.noConsoleMessage());
        }

        assert sender instanceof Player;
        Player p = (Player) sender;
        if (!p.hasPermission(permission)){
            p.sendMessage(ConfigUtils.noPermMessage());
        }

    }

    public static void commandPermissionChecker(CommandSender sender, String permission){
        if (!sender.hasPermission(permission)){
            sender.sendMessage(ConfigUtils.noPermMessage());
        }
    }

    public static String gamemodeName(Player p){
        String gamemode = "";
        switch (p.getGameMode()){
            case CREATIVE:
                gamemode = MyUtils.color("&acriativo");
                break;
            case SURVIVAL:
                gamemode = MyUtils.color("&asobrevivência");
                break;
            case ADVENTURE:
                gamemode = MyUtils.color("&aaventura");
                break;
            case SPECTATOR:
                gamemode = MyUtils.color("&aespectador");
                break;
        }
        return gamemode;
    }

    public static void staffLog(Player exclude, String msg){
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("staff.log") && player != exclude){
                player.sendMessage(MyUtils.color("&7&o[" + msg + "&7&o]"));
            }
        });
        log("&7&o[" + msg + "&7&o]");
    }

    public static void broadcastStaff(String msg){
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("staff.chat") && plugin.getStaffChatOn().contains(player.getUniqueId())){
                player.sendMessage(color(msg));
            }
        });

    }

    public static void staffLog(CommandSender exclude, String msg){
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("staff.log") && player != exclude){
                player.sendMessage(MyUtils.color("&7&o[" + msg + "&7&o]"));
            }
        });
        log("&7&o[" + msg + "&7&o]");
    }

    public static void broadcast(String message){
        String fmsg = ConfigUtils.prefix() + message;
        Bukkit.getOnlinePlayers().forEach(player ->
                player.sendMessage(color(fmsg)));
    }

    public static void message(Player player, String message){
        if (player != null){
            player.sendMessage(MyUtils.color(message));
        }
    }

    public static void message(CommandSender sender, String message){
        if (sender != null){
            sender.sendMessage(MyUtils.color(message));
        }
    }
}