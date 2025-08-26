package br.com.henriplugins.utils;

import org.bukkit.ChatColor;

public class ColorUtils {

    public static String applyColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
