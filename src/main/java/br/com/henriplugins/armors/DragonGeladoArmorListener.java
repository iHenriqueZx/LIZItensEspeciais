package br.com.henriplugins.armors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DragonGeladoArmorListener implements Listener {
    private final Set<UUID> playersWithDragonGelado = new HashSet();
    private final Map<Block, Material> originalBlocks = new HashMap();
    private final Set<Material> blockedMaterials;

    public DragonGeladoArmorListener() {
        this.blockedMaterials = new HashSet(Arrays.asList(Material.CHEST, Material.TRAPPED_CHEST, Material.ENDER_CHEST, Material.SHULKER_BOX, Material.HOPPER, Material.FURNACE, Material.CRAFTING_TABLE, Material.ENCHANTING_TABLE, Material.ANVIL, Material.CHIPPED_ANVIL, Material.DAMAGED_ANVIL, Material.BEACON, Material.BREWING_STAND, Material.ARMOR_STAND, Material.DROPPER, Material.DISPENSER, Material.LADDER, Material.OAK_FENCE, Material.SPRUCE_FENCE, Material.BIRCH_FENCE, Material.JUNGLE_FENCE, Material.ACACIA_FENCE, Material.DARK_OAK_FENCE, Material.NETHER_BRICK_FENCE, Material.OAK_FENCE_GATE, Material.SPRUCE_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.JUNGLE_FENCE_GATE, Material.ACACIA_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.OAK_DOOR, Material.SPRUCE_DOOR, Material.BIRCH_DOOR, Material.JUNGLE_DOOR, Material.ACACIA_DOOR, Material.DARK_OAK_DOOR, Material.IRON_DOOR, Material.TORCH, Material.FLOWER_POT));
    }

    private boolean hasDragonGeladoArmor(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();
        boolean hasHelmet = helmet != null && helmet.hasItemMeta() && helmet.getItemMeta().hasDisplayName() && ChatColor.stripColor(helmet.getItemMeta().getDisplayName()).contains("Dragão Gelado");
        boolean hasChestplate = chestplate != null && chestplate.hasItemMeta() && chestplate.getItemMeta().hasDisplayName() && ChatColor.stripColor(chestplate.getItemMeta().getDisplayName()).contains("Dragão Gelado");
        boolean hasLeggings = leggings != null && leggings.hasItemMeta() && leggings.getItemMeta().hasDisplayName() && ChatColor.stripColor(leggings.getItemMeta().getDisplayName()).contains("Dragão Gelado");
        boolean hasBoots = boots != null && boots.hasItemMeta() && boots.getItemMeta().hasDisplayName() && ChatColor.stripColor(boots.getItemMeta().getDisplayName()).contains("Dragão Gelado");
        return hasHelmet && hasChestplate && hasLeggings && hasBoots;
    }

    private void applyDragonGeladoEffects(Player player) {
        if (this.hasDragonGeladoArmor(player)) {
            this.playersWithDragonGelado.add(player.getUniqueId());
        } else {
            this.playersWithDragonGelado.remove(player.getUniqueId());
        }

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (this.playersWithDragonGelado.contains(player.getUniqueId())) {
            this.removerGelosAntigos();

            for(int x = -2; x <= 2; ++x) {
                for(int z = -2; z <= 2; ++z) {
                    double distance = Math.sqrt((double)(x * x + z * z));
                    if (distance <= 2.5D) {
                        Block block = player.getLocation().add((double)x, -1.0D, (double)z).getBlock();
                        if (!this.blockedMaterials.contains(block.getType()) && !this.originalBlocks.containsKey(block)) {
                            this.originalBlocks.put(block, block.getType());
                            block.setType(Material.ICE);
                            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("LIZItensEspeciais"), () -> {
                                if (this.originalBlocks.containsKey(block)) {
                                    block.setType((Material)this.originalBlocks.get(block));
                                    this.originalBlocks.remove(block);
                                }

                            }, 60L);
                        }
                    }
                }
            }

        }
    }

    private void removerGelosAntigos() {
        Block block;
        for(Iterator var1 = (new HashSet(this.originalBlocks.keySet())).iterator(); var1.hasNext(); this.originalBlocks.remove(block)) {
            block = (Block)var1.next();
            if (block.getType() == Material.ICE) {
                block.setType((Material)this.originalBlocks.get(block));
            }
        }

    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (this.playersWithDragonGelado.contains(player.getUniqueId())) {
            this.removerGelosAntigos();
            Block abaixo = player.getLocation().subtract(0.0D, 2.0D, 0.0D).getBlock();
            if (abaixo.getType() == Material.AIR) {
                player.teleport(player.getLocation().subtract(0.0D, 1.0D, 0.0D));
            }

        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (this.originalBlocks.containsKey(event.getBlock())) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        if (this.originalBlocks.containsKey(event.getBlock())) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.applyDragonGeladoEffects(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.playersWithDragonGelado.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player)event.getWhoClicked();
            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("LIZItensEspeciais"), () -> {
                this.applyDragonGeladoEffects(player);
            }, 1L);
        }

    }
}
