package br.com.henriplugins.listeners;

import br.com.henriplugins.utils.ColorUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TagListener implements Listener {
    private Player renamingPlayer;

    @EventHandler
    public void onTagClick(PlayerInteractEvent event) {
        if (!event.getAction().toString().contains("LEFT_CLICK")) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() != Material.NAME_TAG) return;
        startRenamingProcess(player);
    }

    private void startRenamingProcess(Player player) {
        renamingPlayer = player;

        player.sendMessage("§aDigite o novo nome para a etiqueta:");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (renamingPlayer == null || !event.getPlayer().equals(renamingPlayer)) return;

        event.setCancelled(true);

        String newName = event.getMessage();

        renameTag(renamingPlayer, newName);

        renamingPlayer = null;
    }

    public void renameTag(Player player, String newName) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() != Material.NAME_TAG) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        String coloredName = ColorUtils.applyColors(newName);
        meta.setDisplayName(coloredName);
        item.setItemMeta(meta);

        player.sendMessage("§aEtiqueta renomeada para: §f" + coloredName);
    }
}
