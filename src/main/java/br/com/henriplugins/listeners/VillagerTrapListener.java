package br.com.henriplugins.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.Set;

public class VillagerTrapListener implements Listener {

    private final Set<Block> trapBlocks = new HashSet<>();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.getType() == Material.STONE_PRESSURE_PLATE) {
            ItemStack item = event.getItemInHand();
            if (item != null && item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Armadilha de Aldeão")) {
                    trapBlocks.add(block);
                }
            }
        }
    }

    @EventHandler
    public void onEntityStep(EntityInteractEvent event) {
        Block block = event.getBlock();

        if (block.getType() == Material.STONE_PRESSURE_PLATE && trapBlocks.contains(block)) {
            if (event.getEntity() instanceof Villager) {
                Villager villager = (Villager) event.getEntity();

                villager.remove();

                ItemStack spawnEgg = new ItemStack(Material.VILLAGER_SPAWN_EGG);

                block.getWorld().dropItemNaturally(block.getLocation(), spawnEgg);

                block.getWorld().playSound(block.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0f, 1.0f);

                Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("LIZItensEspeciais"), () -> {
                    block.setType(Material.AIR);
                }, 1L);
            }
        }
    }
}
