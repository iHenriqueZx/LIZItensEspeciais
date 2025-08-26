package br.com.henriplugins.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class EspadaDeusItem {
    public static ItemStack getItem() {
        ItemStack espadadeus = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = espadadeus.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Espada de Deus");
            meta.setLore(Arrays.asList(
                    ChatColor.WHITE + "Dano: " + ChatColor.RED + "+200",
                    ChatColor.GRAY + "",
                    ChatColor.GOLD + "Habilidade: Trovões",
                    ChatColor.GRAY + "Possuí uma chance de cair raios",
                    ChatColor.GRAY + "em seus inimigos, além de ser",
                    ChatColor.GRAY + "forjada pelos deuses e indestrutível.",
                    "",
                    ChatColor.AQUA + "" + ChatColor.BOLD + "ITEM MYSTHICO"
            ));
            espadadeus.setItemMeta(meta);
        }

        return espadadeus;
    }
}
