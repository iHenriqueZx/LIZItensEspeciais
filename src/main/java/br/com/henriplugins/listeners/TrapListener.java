package br.com.henriplugins.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class TrapListener implements Listener {

    private final JavaPlugin plugin;

    public TrapListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onThrowSnowball(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        Player player = (Player) event.getEntity().getShooter();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.SNOWBALL && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName() &&
                    meta.getDisplayName().equals(ChatColor.BLUE + "" + ChatColor.BOLD + "Armadilha")) {
                event.getEntity().setCustomName("Armadilha");
            }
        }
    }

    @EventHandler
    public void onSnowballHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball)) return;
        Snowball snowball = (Snowball) event.getEntity();

        if (snowball.getCustomName() == null || !snowball.getCustomName().equals("Armadilha")) return;

        Block hitBlock = snowball.getLocation().getBlock();

        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                Block block = hitBlock.getRelative(x, 1, z);
                if (block.getType() == Material.AIR) {
                    block.setType(Material.COBWEB);
                }
            }

        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    Block block = hitBlock.getRelative(x, 1, z);
                    if (block.getType() == Material.COBWEB) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }, 20L * 30);
    }
}
