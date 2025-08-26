package br.com.henriplugins.listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FerramentasListener implements Listener {

    private final JavaPlugin plugin;

    private final Set<Material> blocosQuePaQuebra = new HashSet<>(Arrays.asList(
            Material.DIRT, Material.GRASS_BLOCK, Material.SAND, Material.GRAVEL, Material.CLAY, Material.SOUL_SAND,
            Material.RED_SAND, Material.COARSE_DIRT, Material.PODZOL
    ));

    private final Set<String> mundosRestritos = new HashSet<>(Arrays.asList(
            "spawn", "eventos", "dungeons", "afk", "end"
    ));

    public FerramentasListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void aoQuebrarBloco(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (mundosRestritos.contains(player.getWorld().getName())) return;

        ItemStack item = player.getItemInHand();

        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) return;

        String nome = ChatColor.stripColor(meta.getDisplayName());

        boolean ehPicareta = nome.equalsIgnoreCase("Picareta de Deus");
        boolean ehPa = nome.equalsIgnoreCase("Pá de Deus");

        if (ehPicareta || ehPa) {
            Material tipoBloco = event.getBlock().getType();
            if (ehPa && !blocosQuePaQuebra.contains(tipoBloco)) return;
            if (ehPicareta && (tipoBloco == Material.BEDROCK || tipoBloco == Material.BARRIER || tipoBloco == Material.SPAWNER || tipoBloco == Material.DIRT || tipoBloco == Material.GRASS_BLOCK || tipoBloco == Material.SAND || tipoBloco == Material.GRAVEL)) return;

            event.setCancelled(true);
            quebrarAreaComBaseNaDirecao(event.getBlock(), player, item, ehPicareta);
            item.setDurability((short) (item.getDurability() + 1));
        }
    }

    private void quebrarAreaComBaseNaDirecao(Block base, Player player, ItemStack ferramenta, boolean ehPicareta) {
        Vector direction = player.getLocation().getDirection().normalize();
        double pitch = player.getLocation().getPitch();

        int[][] offsets = new int[9][3];
        int index = 0;

        if (pitch < -45 || pitch > 45) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    offsets[index++] = new int[]{x, 0, z};
                }
            }
        } else {
            boolean olhandoLado = Math.abs(direction.getX()) > Math.abs(direction.getZ());

            if (olhandoLado) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        offsets[index++] = new int[]{0, y, z};
                    }
                }
            } else {
                for (int y = -1; y <= 1; y++) {
                    for (int x = -1; x <= 1; x++) {
                        offsets[index++] = new int[]{x, y, 0};
                    }
                }
            }
        }

        for (int[] offset : offsets) {
            Block alvo = base.getRelative(offset[0], offset[1], offset[2]);
            Material tipo = alvo.getType();

            if (tipo == Material.AIR) continue;

            if (ehPicareta && tipo != Material.BEDROCK && tipo != Material.BARRIER) {
                alvo.breakNaturally(ferramenta);
            } else if (!ehPicareta && blocosQuePaQuebra.contains(tipo)) {
                alvo.breakNaturally(ferramenta);
            }
        }
    }

    @EventHandler
    public void aoArar(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        if (mundosRestritos.contains(player.getWorld().getName())) return;

        ItemStack item = player.getItemInHand();
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) return;

        String nome = ChatColor.stripColor(meta.getDisplayName());
        if (!nome.equalsIgnoreCase("Enxada de Deus")) return;

        Block base = event.getClickedBlock();
        if (base == null) return;

        event.setCancelled(true);

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                Block target = base.getRelative(dx, 0, dz);
                arar(target);
            }
        }

        item.setDurability((short) (item.getDurability() + 1));
    }

    private void arar(Block b) {
        if (isBlocoAravel(b.getType())) {
            Block acima = b.getRelative(BlockFace.UP);
            if (acima.getType() == Material.AIR) {
                b.setType(Material.FARMLAND);
            }
        }
    }

    private boolean isBlocoAravel(Material material) {
        return material == Material.DIRT || material == Material.GRASS_BLOCK || material == Material.COARSE_DIRT || material == Material.PODZOL;
    }
}
