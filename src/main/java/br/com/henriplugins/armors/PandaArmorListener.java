package br.com.henriplugins.armors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PandaArmorListener implements Listener {

    private final Set<UUID> playersWithPanda = new HashSet<>();

    private boolean hasPandaArmor(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        boolean hasPandaHelmet = helmet != null && helmet.hasItemMeta() && helmet.getItemMeta().hasDisplayName() && ChatColor.stripColor(helmet.getItemMeta().getDisplayName()).contains("Panda");
        boolean hasPandaChestplate = chestplate != null && chestplate.hasItemMeta() && chestplate.getItemMeta().hasDisplayName() && ChatColor.stripColor(chestplate.getItemMeta().getDisplayName()).contains("Panda");
        boolean hasPandaLeggings = leggings != null && leggings.hasItemMeta() && leggings.getItemMeta().hasDisplayName() && ChatColor.stripColor(leggings.getItemMeta().getDisplayName()).contains("Panda");
        boolean hasPandaBoots = boots != null && boots.hasItemMeta() && boots.getItemMeta().hasDisplayName() && ChatColor.stripColor(boots.getItemMeta().getDisplayName()).contains("Panda");

        return hasPandaHelmet && hasPandaChestplate && hasPandaLeggings && hasPandaBoots;
    }

    private void applyPandaEffects(Player player) {
        boolean isWearingPanda = hasPandaArmor(player);

        if (isWearingPanda && !playersWithPanda.contains(player.getUniqueId())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0, true, false, false)); // Regeneração
            playersWithPanda.add(player.getUniqueId());
        } else if (!isWearingPanda && playersWithPanda.contains(player.getUniqueId())) {
            player.removePotionEffect(PotionEffectType.REGENERATION);
            playersWithPanda.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        applyPandaEffects(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (playersWithPanda.contains(player.getUniqueId())) {
            player.removePotionEffect(PotionEffectType.REGENERATION);
            playersWithPanda.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerInventoryChange(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("LIZItensEspeciais"), () -> applyPandaEffects(player), 1L);
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL && playersWithPanda.contains(player.getUniqueId())) {
                event.setCancelled(true); // Cancela apenas o dano de queda
            }
        }
    }
}
