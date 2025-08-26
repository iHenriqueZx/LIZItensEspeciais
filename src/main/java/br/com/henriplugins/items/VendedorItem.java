package br.com.henriplugins.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class VendedorItem {
    public static ItemStack getItem() {
        ItemStack vendedor = new ItemStack(Material.CHEST);
        ItemMeta meta = vendedor.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Vendedor Automático");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Use para vender seus itens",
                    ChatColor.GRAY + "de forma automática enquanto",
                    ChatColor.GRAY + "estiver online!",
                    "",
                    ChatColor.GOLD + "" + ChatColor.BOLD + "ITEM LENDÁRIO"
            ));
            vendedor.setItemMeta(meta);
        }

        return vendedor;
    }
}
