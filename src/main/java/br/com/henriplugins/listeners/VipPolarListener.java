package br.com.henriplugins.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VipPolarListener implements Listener {

    @EventHandler
    public void aoUsarItem(PlayerInteractEvent event) {
        Player jogador = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || item.getType() != Material.PAPER) return;
        if (!item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) return;

        if (meta.getDisplayName().equals(ChatColor.BLUE + "" + ChatColor.BOLD + "Vip [Polar]")) {
            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                jogador.getInventory().removeItem(item);
            }

            String comando = String.format("darvip %s polar 3", jogador.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), comando);
        }
    }
}
