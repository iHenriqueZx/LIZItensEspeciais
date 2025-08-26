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
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DragonZombieArmorListener implements Listener {

    private final Set<UUID> playersWithDragonZombie = new HashSet<>();
    private final HashMap<UUID, Integer> lastHungerLevel = new HashMap<>();

    private boolean hasDragonZombieArmor(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        boolean hasHelmet = helmet != null && helmet.hasItemMeta() && helmet.getItemMeta().hasDisplayName() &&
                ChatColor.stripColor(helmet.getItemMeta().getDisplayName()).contains("Dragão Zombie");

        boolean hasChestplate = chestplate != null && chestplate.hasItemMeta() && chestplate.getItemMeta().hasDisplayName() &&
                ChatColor.stripColor(chestplate.getItemMeta().getDisplayName()).contains("Dragão Zombie");

        boolean hasLeggings = leggings != null && leggings.hasItemMeta() && leggings.getItemMeta().hasDisplayName() &&
                ChatColor.stripColor(leggings.getItemMeta().getDisplayName()).contains("Dragão Zombie");

        boolean hasBoots = boots != null && boots.hasItemMeta() && boots.getItemMeta().hasDisplayName() &&
                ChatColor.stripColor(boots.getItemMeta().getDisplayName()).contains("Dragão Zombie");

        return hasHelmet && hasChestplate && hasLeggings && hasBoots;
    }

    private void applyDragonZombieEffects(Player player) {
        boolean isWearingDragonZombie = hasDragonZombieArmor(player);

        if (isWearingDragonZombie && !playersWithDragonZombie.contains(player.getUniqueId())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, Integer.MAX_VALUE, 0, true, false, false)); // Efeito de fome
            playersWithDragonZombie.add(player.getUniqueId());
            lastHungerLevel.put(player.getUniqueId(), player.getFoodLevel()); // Salva o nível de fome inicial
        } else if (!isWearingDragonZombie && playersWithDragonZombie.contains(player.getUniqueId())) {
            player.removePotionEffect(PotionEffectType.HUNGER);
            playersWithDragonZombie.remove(player.getUniqueId());
            lastHungerLevel.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        applyDragonZombieEffects(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (playersWithDragonZombie.contains(player.getUniqueId())) {
            player.removePotionEffect(PotionEffectType.HUNGER);
            playersWithDragonZombie.remove(player.getUniqueId());
            lastHungerLevel.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerInventoryChange(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("LIZItensEspeciais"), () -> applyDragonZombieEffects(player), 1L);
        }
    }

    @EventHandler
    public void onHungerChange(org.bukkit.event.entity.FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (playersWithDragonZombie.contains(player.getUniqueId())) {
                int lastHunger = lastHungerLevel.getOrDefault(player.getUniqueId(), 20);
                int newHunger = event.getFoodLevel();

                if (newHunger < lastHunger) { // Se o jogador perdeu fome
                    int heartsToHeal = lastHunger - newHunger;
                    double newHealth = Math.min(player.getHealth() + heartsToHeal, player.getMaxHealth());
                    player.setHealth(newHealth);
                }

                lastHungerLevel.put(player.getUniqueId(), newHunger);
            }
        }
    }

    @EventHandler
    public void onPlayerEat(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        if (playersWithDragonZombie.contains(player.getUniqueId())) {
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("LIZItensEspeciais"), () -> {
                lastHungerLevel.put(player.getUniqueId(), player.getFoodLevel());
            }, 1L);
        }
    }
}
