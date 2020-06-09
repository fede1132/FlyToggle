package com.gmail.fendt873.flytoggle.cmds;

import com.gmail.fendt873.flytoggle.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;


public class CmdGiveFlyItem implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if ((!(sender instanceof Player))) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "This command is only for player");
            return false;
        }
        if (sender.hasPermission(Util.getFromConfig("Fly.Item.Give-Permission"))) {
            String i = Util.getFromConfig("Fly.Item.ID");
            try {
                Material m = Material.getMaterial(i);
                ItemStack item = new ItemStack(m);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Util.getFromConfig("Fly.Item.Name"));
                meta.setLore(Util.getListFromConfig("Fly.Item.Lore"));
                item.setItemMeta(meta);
                ((Player) sender).getInventory().addItem(item);
            } catch (EnumConstantNotPresentException e) {
                sender.sendMessage(Util.getFromConfig("Fly.Item.Invalid-Material"));
            }
        }
        return true;
    }
}
