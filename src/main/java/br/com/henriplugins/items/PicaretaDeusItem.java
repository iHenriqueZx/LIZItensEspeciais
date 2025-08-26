package br.com.henriplugins.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class PicaretaDeusItem {
    public static ItemStack getItem() {
        ItemStack picaretadeus = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta meta = picaretadeus.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Picareta de Deus");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Use para quebrar uma",
                    ChatColor.GRAY + "área 3x3!",
                    "",
                    ChatColor.AQUA + "" + ChatColor.BOLD + "ITEM MYSTHICO"
            ));
            picaretadeus.setItemMeta(meta);
        }

        return picaretadeus;
    }
}
