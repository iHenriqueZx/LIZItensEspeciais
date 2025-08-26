package br.com.henriplugins.commands;

import br.com.henriplugins.items.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveItemCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(ChatColor.RED + "Uso correto: /lizitem <nick> <quantia> <item>");
            return false;
        } else {
            String playerName = args[0];

            int quantity;
            try {
                quantity = Integer.parseInt(args[1]);
            } catch (NumberFormatException var10) {
                sender.sendMessage(ChatColor.RED + "A quantia deve ser um número válido!");
                return false;
            }

            Player targetPlayer = Bukkit.getPlayer(playerName);
            if (targetPlayer == null) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado!");
                return false;
            } else {
                ItemStack item;
                if (args[2].equalsIgnoreCase("armadilha")) {
                    item = VillagerTrapItem.getItem();
                } else if (args[2].equalsIgnoreCase("fornalha")) {
                    item = FornalhaVelozItem.getItem();
                } else if (args[2].equalsIgnoreCase("vippolar")) {
                    item = VipPolarItem.getItem();
                } else if (args[2].equalsIgnoreCase("vippardo")) {
                    item = VipPardoItem.getItem();
                } else if (args[2].equalsIgnoreCase("trap")) {
                    item = TrapItem.getItem();
                } else if (args[2].equalsIgnoreCase("montaria")) {
                    item = MontariaItem.getItem();
                } else if (args[2].equalsIgnoreCase("vendedor")) {
                    item = VendedorItem.getItem();
                } else if (args[2].equalsIgnoreCase("picaretadeus")) {
                    item = PicaretaDeusItem.getItem();
                } else if (args[2].equalsIgnoreCase("padeus")) {
                    item = PaDeusItem.getItem();
                } else if (args[2].equalsIgnoreCase("enxadadeus")) {
                    item = EnxadaDeusItem.getItem();
                } else if (args[2].equalsIgnoreCase("espadadeus")) {
                    item = EspadaDeusItem.getItem();
                } else {
                    Material material = Material.getMaterial(args[2].toUpperCase());
                    if (material == null) {
                        sender.sendMessage(ChatColor.RED + "O item fornecido não existe!");
                        return false;
                    }

                    item = new ItemStack(material);
                }

                item.setAmount(quantity);
                targetPlayer.getInventory().addItem(new ItemStack[]{item});
                ChatColor var10001 = ChatColor.GREEN;
                sender.sendMessage(var10001 + "Você deu " + quantity + " " + item.getItemMeta().getDisplayName() + ChatColor.GREEN + " para " + targetPlayer.getName() + ".");
                targetPlayer.sendMessage(ChatColor.GREEN + "Você recebeu " + quantity + " " + item.getItemMeta().getDisplayName());
                return true;
            }
        }
    }
}
