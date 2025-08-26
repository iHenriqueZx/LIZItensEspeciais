package br.com.henriplugins.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class EnxadaDeusItem {
    public static ItemStack getItem() {
        ItemStack enxadadeus = new ItemStack(Material.NETHERITE_HOE);
        ItemMeta meta = enxadadeus.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Enxada de Deus");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Use para arar uma",
                    ChatColor.GRAY + "área 3x3!",
                    "",
                    ChatColor.AQUA + "" + ChatColor.BOLD + "ITEM MYSTHICO"
            ));
            enxadadeus.setItemMeta(meta);
        }

        return enxadadeus;
    }
}
