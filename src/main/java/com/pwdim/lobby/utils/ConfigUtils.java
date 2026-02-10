package com.pwdim.lobby.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

public class ConfigUtils {

    public static ConfigurationSection section(String section){
        return Bukkit.getPluginManager().getPlugin("Lobby").getConfig().getConfigurationSection(section);
    }

    public static String noPermMessage(){
        String msg = section("server").getString("no-perm-message", "&cSem permissão!");


        return MyUtils.color(msg);
    }

    public static String noCommandMessage(){
        String msg = section("server").getString("no-cmd-message", "&cNão encontrado.");
        return MyUtils.color(msg);
    }

    public static String noConsoleMessage(){
        String msg = section("server").getString("no-console-message", "&cPrecisa ser um jogador!");
        return MyUtils.color(msg);
    }

    public static String prefix(){
        String prefix = section("server").getString("prefix", "&b&lPWDIM");
        return MyUtils.color(prefix + " &r");
    }
}
