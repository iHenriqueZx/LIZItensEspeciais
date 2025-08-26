package br.com.henriplugins.armors;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ObsidianArmorListener implements Listener {

    private final Set<UUID> playersWithFullSet = new HashSet<>();
    private final Map<Location, Material> revertedBlocks = new HashMap<>();

    private boolean isObsidianArmorPiece(ItemStack item, String displayName, String itemsAdderId) {
        if (item == null) return false;
        CustomStack customStack = CustomStack.byItemStack(item);
        if (customStack != null && customStack.getId().equalsIgnoreCase(itemsAdderId)) {
            return true;
        }
        return item.hasItemMeta()
                && item.getItemMeta().hasDisplayName()
                && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase(displayName);
    }

    private boolean isWearingFullObsidian(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chest = player.getInventory().getChestplate();
        ItemStack legs = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        return isObsidianArmorPiece(helmet, "Capacete De Obsidian", "obsidian_helmet")
                && isObsidianArmorPiece(chest, "Peitoral De Obsidian", "obsidian_chestplate")
                && isObsidianArmorPiece(legs, "Calça De Obsidian", "obsidian_leggings")
                && isObsidianArmorPiece(boots, "Bota De Obsidian", "obsidian_boots");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        boolean isWearingFull = isWearingFullObsidian(player);

        if (isWearingFull) {
            Location loc = player.getLocation();
            World world = player.getWorld();

            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    Location checkLoc = loc.clone().add(x, -1, z);
                    Block block = world.getBlockAt(checkLoc);

                    if (block.getType() == Material.LAVA) {
                        block.setType(Material.OBSIDIAN);
                        revertedBlocks.put(checkLoc, Material.LAVA);

                        Bukkit.getScheduler().runTaskLater(
                                Bukkit.getPluginManager().getPlugin("LIZItensEspeciais"), () -> {
                                    if (revertedBlocks.containsKey(checkLoc) && block.getType() == Material.OBSIDIAN) {
                                        block.setType(Material.LAVA);
                                        revertedBlocks.remove(checkLoc);
                                    }
                                }, 100L
                        );
                    }
                }
            }
        }
    }

    @EventHandler
    public void onLavaDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        if (event.getCause() == EntityDamageEvent.DamageCause.LAVA
                || event.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR) {

            if (isWearingFullObsidian(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();

        if (item == null) return;

        // Checa tanto pelo ItemsAdder quanto pelo displayName
        if (isObsidianArmorPiece(item, "Capacete De Obsidian", "obsidian_helmet")
                || isObsidianArmorPiece(item, "Peitoral De Obsidian", "obsidian_chestplate")
                || isObsidianArmorPiece(item, "Calça De Obsidian", "obsidian_leggings")
                || isObsidianArmorPiece(item, "Bota De Obsidian", "obsidian_boots")) {

            if (Math.random() <= 0.8) {
                event.setCancelled(true);
            }
        }
    }
}
