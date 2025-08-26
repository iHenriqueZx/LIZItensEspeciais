package br.com.henriplugins.items;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class VipPolarItem {

    public static ItemStack getItem() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Vip [Polar]");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Ative para receber o vip polar",ChatColor.GRAY + "por 3 dias!", "", ChatColor.BLUE + "" + ChatColor.BOLD + "ITEM RARO"
            ));
            item.setItemMeta(meta);
        }
        return item;
    }
}
