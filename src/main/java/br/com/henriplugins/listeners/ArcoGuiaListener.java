package br.com.henriplugins.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Comparator;

public class ArcoGuiaListener implements Listener {

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!(event.getProjectile() instanceof Arrow arrow)) return;

        ItemStack bow = event.getBow();
        if (bow == null || bow.getType() != Material.BOW) return;

        ItemMeta meta = bow.getItemMeta();
        if (meta == null || meta.getDisplayName() == null) return;

        String displayName = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Arco Guia";
        if (!meta.getDisplayName().equals(displayName)) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isDead() || arrow.isOnGround()) {
                    cancel();
                    return;
                }

                Entity target = arrow.getNearbyEntities(20, 20, 20).stream()
                        .filter(e -> e instanceof LivingEntity && !(e instanceof Player))
                        .min(Comparator.comparingDouble(e -> e.getLocation().distanceSquared(arrow.getLocation())))
                        .orElse(null);

                if (target != null) {
                    Vector direction = target.getLocation().add(0, 1, 0).toVector().subtract(arrow.getLocation().toVector()).normalize();
                    arrow.setVelocity(direction.multiply(2));
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugins()[0], 0L, 1L); // Executa todo tick
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow arrow)) return;

        if (!(arrow.getShooter() instanceof Player player)) return;

        ItemStack bow = player.getInventory().getItemInMainHand();
        if (bow == null || bow.getType() != Material.BOW) return;

        ItemMeta meta = bow.getItemMeta();
        if (meta == null || meta.getDisplayName() == null) return;

        String displayName = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Arco Guia";
        if (!meta.getDisplayName().equals(displayName)) return;

        event.setDamage(30.0);
    }
}
