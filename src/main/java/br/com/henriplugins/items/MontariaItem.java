package br.com.henriplugins.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MontariaItem {

    public static ItemStack getItem() {
        ItemStack saddle = new ItemStack(Material.SADDLE);
        ItemMeta meta = saddle.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Montaria");
            meta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "Use para montar em", ChatColor.GRAY + "algum jogador!", ChatColor.GRAY + "", ChatColor.GREEN + "" + ChatColor.BOLD + "ITEM INCOMUM"));
            saddle.setItemMeta(meta);
        }

        return saddle;
    }
}
