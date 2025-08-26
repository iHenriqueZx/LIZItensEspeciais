package br.com.henriplugins.listeners;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.user.SkillsUser;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class TarantulaSwordListener implements Listener {
    private final Map<Player, Long> cooldowns = new HashMap();
    private final Map<Player, Boolean> usingAbility = new HashMap();

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && item != null && item.getType() == Material.NETHERITE_SWORD) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName() && ChatColor.stripColor(meta.getDisplayName()).equals("Espada de Tarântula")) {
                AuraSkillsApi auraSkills = AuraSkillsApi.get();
                SkillsUser user = auraSkills.getUser(player.getUniqueId());
                double mana = user.getMana();
                if (mana >= 20.0D) {
                    if (this.cooldowns.containsKey(player) && System.currentTimeMillis() - (Long)this.cooldowns.get(player) < 5000L) {
                        player.sendMessage(ChatColor.RED + "Espere 5 segundos para usar novamente a habilidade.");
                        return;
                    }

                    this.cooldowns.put(player, System.currentTimeMillis());
                    user.setMana(mana - 20.0D);
                    Vector velocity = new Vector(0, 2, 0);
                    player.setVelocity(velocity);
                    this.usingAbility.put(player, true);
                } else {
                    player.sendMessage(ChatColor.RED + "Você não tem mana suficiente para usar essa habilidade!");
                }
            }
        }

    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (event.getCause() == DamageCause.FALL && (Boolean)this.usingAbility.getOrDefault(player, false)) {
                event.setCancelled(true);
                Iterator var3 = player.getWorld().getNearbyEntities(player.getLocation(), 10.0D, 10.0D, 10.0D).iterator();

                while(var3.hasNext()) {
                    Entity entity = (Entity)var3.next();
                    if (entity instanceof LivingEntity && entity != player) {
                        ((LivingEntity)entity).damage(100.0D);
                    }
                }

                this.usingAbility.remove(player);
            }
        }

    }
}