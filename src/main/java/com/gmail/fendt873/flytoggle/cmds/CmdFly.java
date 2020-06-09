package com.gmail.fendt873.flytoggle.cmds;

import com.gmail.fendt873.flytoggle.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CmdFly implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if ((!(sender instanceof Player))) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "This command is only for player");
            return false;
        }
        Player player = (Player)sender;
        if ((args.length == 0) && (player.hasPermission(Util.getFromConfig("Fly.Permission"))))
        {
            if (player.getAllowFlight())
            {
                player.setAllowFlight(false);
                player.sendMessage(Util.getFromConfig("Fly.Fly-Disabled"));
            }
            else if (!player.getAllowFlight())
            {
                player.setAllowFlight(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Util.getFromConfig("Fly.Fly-Enabled")));
            }
        }
        else if (!player.hasPermission(Util.getFromConfig("Fly.Permission")))
        {
            player.sendMessage(Util.getFromConfig("Fly.No-Perms"));
        }
        else if ((args.length >= 1) && (player.hasPermission(Util.getFromConfig("Fly.Other.Permission"))))
        {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(Util.getFromConfig("Fly.Other.Null-Target").replaceAll("%target%", args[0]));
            }

            if (target.getAllowFlight())
            {
                target.setAllowFlight(false);
                target.sendMessage(Util.getFromConfig("Fly.Other.To-Disabled").replaceAll("%by%", player.getName()));
                player.sendMessage(Util.getFromConfig("Fly.Other.By-Disabled").replaceAll("%to%", target.getName()));
            }
            else if (!target.getAllowFlight())
            {
                target.setAllowFlight(true);
                target.sendMessage(Util.getFromConfig("Fly.Other.To-Enabled").replaceAll("%by%", player.getName()));
                player.sendMessage(Util.getFromConfig("Fly.Other.By-Enabled").replaceAll("%to%", target.getName()));
            }


        }
        else if ((!player.hasPermission(Util.getFromConfig("Fly.Other.Permission"))) && (player.hasPermission("Fly.Permission")))
        {
            if (player.getAllowFlight())
            {
                player.setAllowFlight(false);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Util.getFromConfig("Fly.Fly-Disabled")));
            }
            else if (!player.getAllowFlight())
            {
                player.setAllowFlight(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Util.getFromConfig("Fly.Fly-Enabled")));
            }
        }

        return true;
    }
}
