package br.com.henriplugins.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class VipPardoItem {

    public static ItemStack getItem(){
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Vip [Pardo]");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Ative para receber o vip pardo", ChatColor.GRAY + "por 3 dias!", "", ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "ITEM ÉPICO"
            ));
            item.setItemMeta(meta);
        }
        return item;
    }
}
