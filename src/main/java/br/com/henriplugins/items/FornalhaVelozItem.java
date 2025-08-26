package br.com.henriplugins.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FornalhaVelozItem {

    public static ItemStack getItem() {
        ItemStack fornalha = new ItemStack(Material.FURNACE);
        ItemMeta meta = fornalha.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Fornalha Veloz");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Use para esquentar suas coisas",
                    ChatColor.GRAY + "muito mais rápido que o normal!",
                    "",
                    ChatColor.WHITE + "" + ChatColor.BOLD + "ITEM COMUM"
            ));
            fornalha.setItemMeta(meta);
        }

        return fornalha;
    }
}
