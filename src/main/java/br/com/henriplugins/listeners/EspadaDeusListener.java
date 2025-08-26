package br.com.henriplugins.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class EspadaDeusListener implements Listener {

    private final Random random = new Random();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if (!(event.getEntity() instanceof LivingEntity target)) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (!isEspadaDeus(item)) return;

        event.setDamage(200.0); // Define dano para 200

        if (random.nextDouble() <= 0.20) { // 20% de chance
            target.getWorld().strikeLightningEffect(target.getLocation());
            target.damage(8.0, player); // 2.0 = 1 coração
        }
    }
    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        if (isEspadaDeus(item)) {
            event.setCancelled(true);
        }
    }
    private boolean isEspadaDeus(ItemStack item) {
        if (item == null || item.getType() != Material.NETHERITE_SWORD) return false;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) return false;

        return meta.getDisplayName().equals(ChatColor.AQUA + "" + ChatColor.BOLD + "Espada de Deus");
    }
}
