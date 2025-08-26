package br.com.henriplugins.items;

import br.com.henriplugins.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TrapItem {
    public static ItemStack getItem() {
        return new ItemBuilder(Material.SNOWBALL)
                .setName(ChatColor.BLUE + "" + ChatColor.BOLD + "Armadilha")
                .setLore(
                        ChatColor.GRAY + "Use para prender mobs", ChatColor.GRAY + "ou jogadores em teias por", ChatColor.GREEN + "30 " + ChatColor.GRAY + "segundos.", ChatColor.GRAY + "", ChatColor.BLUE + "" + ChatColor.BOLD + "ITEM RARO"
                )
                .build();
    }
}
