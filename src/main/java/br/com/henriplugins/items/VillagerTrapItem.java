package br.com.henriplugins.items;

import br.com.henriplugins.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class VillagerTrapItem {

    public static ItemStack getItem() {
        return new ItemBuilder(Material.STONE_PRESSURE_PLATE)
                .setName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Armadilha de Aldeão")
                .setLore(
                        ChatColor.GRAY + "Use para capturar qualquer", ChatColor.GRAY + "aldeão que pisar nela!", ChatColor.GRAY + "", ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "ITEM ÉPICO"
                )
                .build();
    }
}
