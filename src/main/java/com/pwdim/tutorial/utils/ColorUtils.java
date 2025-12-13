package com.pwdim.tutorial.utils;

import org.bukkit.ChatColor;

public class ColorUtils {

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String color(String message, Object... args) {
        String formattedMessage = String.format(message, args);
        return ChatColor.translateAlternateColorCodes('&', formattedMessage);
    }
}