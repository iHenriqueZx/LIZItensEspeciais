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

public class PardoArmorListener implements Listener {

    private final Set<UUID> playersWithPardo = new HashSet<>();

    private boolean hasPardoArmor(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        boolean hasPardoHelmet = helmet != null && helmet.hasItemMeta() && helmet.getItemMeta().hasDisplayName() && ChatColor.stripColor(helmet.getItemMeta().getDisplayName()).contains("Pardo");
        boolean hasPardoChestplate = chestplate != null && chestplate.hasItemMeta() && chestplate.getItemMeta().hasDisplayName() && ChatColor.stripColor(chestplate.getItemMeta().getDisplayName()).contains("Pardo");
        boolean hasPardoLeggings = leggings != null && leggings.hasItemMeta() && leggings.getItemMeta().hasDisplayName() && ChatColor.stripColor(leggings.getItemMeta().getDisplayName()).contains("Pardo");
        boolean hasPardoBoots = boots != null && boots.hasItemMeta() && boots.getItemMeta().hasDisplayName() && ChatColor.stripColor(boots.getItemMeta().getDisplayName()).contains("Pardo");

        return hasPardoHelmet && hasPardoChestplate && hasPardoLeggings && hasPardoBoots;
    }

    private void applyPardoEffects(Player player) {
        boolean isWearingPardo = hasPardoArmor(player);

        if (isWearingPardo && !playersWithPardo.contains(player.getUniqueId())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0, true, false, false)); // Força
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, true, false, false)); // Resistência
            playersWithPardo.add(player.getUniqueId());
        } else if (!isWearingPardo && playersWithPardo.contains(player.getUniqueId())) {
            player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            playersWithPardo.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        applyPardoEffects(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (playersWithPardo.contains(player.getUniqueId())) {
            player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            playersWithPardo.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerInventoryChange(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("LIZItensEspeciais"), () -> applyPardoEffects(player), 1L);
        }
    }
}
