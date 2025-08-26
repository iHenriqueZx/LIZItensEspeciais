package br.com.henriplugins.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MontariaListener implements Listener {

    @EventHandler
    public void onPlayerUseMontaria(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item != null && item.getType() == Material.SADDLE && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            if (meta != null && meta.getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Montaria")) {
                if (event.getRightClicked() instanceof Player) {
                    Player alvo = (Player) event.getRightClicked();

                    alvo.addPassenger(player);
                }
            }
        }
    }
}
