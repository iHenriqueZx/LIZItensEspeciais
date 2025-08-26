package br.com.henriplugins.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FornalhaVelozListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onFurnaceStartSmelt(FurnaceStartSmeltEvent event) {
        if (event.getBlock().getState() instanceof Furnace) {
            Furnace furnace = (Furnace) event.getBlock().getState();

            if (furnace.getCustomName() != null && furnace.getCustomName().equals(ChatColor.WHITE + "" + ChatColor.BOLD + "Fornalha Veloz")) {
                int originalCookTime = event.getTotalCookTime();
                int acceleratedCookTime = (int) (originalCookTime * 0.04); // Fundição, quanto menor o valor, mais rápido.
                event.setTotalCookTime(acceleratedCookTime);
            } else{

            }
        }
    }
}
