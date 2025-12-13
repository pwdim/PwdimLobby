package com.pwdim.tutorial;

import com.pwdim.tutorial.commands.TestCommand;
import com.pwdim.tutorial.events.ChatEvent;
import com.pwdim.tutorial.events.LobbyListener;
import com.pwdim.tutorial.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TUTORIAL extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Bukkit.getLogger().info(ColorUtils.color("&b Plugin iniciado com sucesso!") );


        getServer().getPluginManager().registerEvents(new LobbyListener(), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);

        getCommand("broadcast").setExecutor(new TestCommand());
    }



    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ColorUtils.color("&c Plugin desligado com sucesso!") );
    }
}
