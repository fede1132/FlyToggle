package com.gmail.fendt873.flytoggle.events;

import com.gmail.fendt873.flytoggle.FlyToggle;
import com.gmail.fendt873.flytoggle.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getPlayer().hasPermission(Util.getFromConfig("Fly.Item.Permission"))) {
            if (event.getPlayer().getItemInHand().getType() != Material.AIR) {
                if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    String itemName = Util.getFromConfig("Fly.Item.Name");
                    if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(itemName)) {
                        if (!FlyToggle.getInstance().use.contains(event.getPlayer())) {
                            Bukkit.dispatchCommand(event.getPlayer(), "fly");
                            FlyToggle.getInstance().use.add(event.getPlayer());
                            Bukkit.getScheduler().runTaskLaterAsynchronously(FlyToggle.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    FlyToggle.getInstance().use.remove(event.getPlayer());
                                }
                            }, Util.getIntFromConfig("Fly.Item.Delay"));
                        }
                    }
                }
            }
        }
    }
}