package br.com.henriplugins.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class PaDeusItem {
    public static ItemStack getItem() {
        ItemStack padeus = new ItemStack(Material.NETHERITE_SHOVEL);
        ItemMeta meta = padeus.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Pá de Deus");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Use para quebrar uma",
                    ChatColor.GRAY + "área 3x3!",
                    "",
                    ChatColor.AQUA + "" + ChatColor.BOLD + "ITEM MYSTHICO"
            ));
            padeus.setItemMeta(meta);
        }

        return padeus;
    }
}
