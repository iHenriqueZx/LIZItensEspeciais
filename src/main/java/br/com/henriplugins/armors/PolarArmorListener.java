package br.com.henriplugins.armors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PolarArmorListener implements Listener {

    private final Set<UUID> playersWithPolar = new HashSet<>();

    private boolean hasPolarArmor(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        boolean hasPolarHelmet = helmet != null && helmet.hasItemMeta() && helmet.getItemMeta().hasDisplayName() && ChatColor.stripColor(helmet.getItemMeta().getDisplayName()).contains("Polar");
        boolean hasPolarChestplate = chestplate != null && chestplate.hasItemMeta() && chestplate.getItemMeta().hasDisplayName() && ChatColor.stripColor(chestplate.getItemMeta().getDisplayName()).contains("Polar");
        boolean hasPolarLeggings = leggings != null && leggings.hasItemMeta() && leggings.getItemMeta().hasDisplayName() && ChatColor.stripColor(leggings.getItemMeta().getDisplayName()).contains("Polar");
        boolean hasPolarBoots = boots != null && boots.hasItemMeta() && boots.getItemMeta().hasDisplayName() && ChatColor.stripColor(boots.getItemMeta().getDisplayName()).contains("Polar");

        return hasPolarHelmet && hasPolarChestplate && hasPolarLeggings && hasPolarBoots;
    }

    private void applyPolarEffects(Player player) {
        boolean isWearingPolar = hasPolarArmor(player);

        if (isWearingPolar && !playersWithPolar.contains(player.getUniqueId())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, true, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, 0, true, false, false));
            playersWithPolar.add(player.getUniqueId());
        } else if (!isWearingPolar && playersWithPolar.contains(player.getUniqueId())) {
            player.removePotionEffect(PotionEffectType.WATER_BREATHING);
            player.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
            playersWithPolar.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        applyPolarEffects(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (playersWithPolar.contains(player.getUniqueId())) {
            player.removePotionEffect(PotionEffectType.WATER_BREATHING);
            player.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
            playersWithPolar.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerInventoryChange(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("LIZItensEspeciais"), () -> applyPolarEffects(player), 1L);
        }
    }
}
