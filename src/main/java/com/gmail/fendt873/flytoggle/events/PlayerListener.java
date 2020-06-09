package com.gmail.fendt873.flytoggle.events;

import com.gmail.fendt873.flytoggle.FlyToggle;
import com.gmail.fendt873.flytoggle.util.SpigotUpdater;
import com.gmail.fendt873.flytoggle.util.Util;
import de.leonhard.storage.Json;
import de.leonhard.storage.shaded.json.JSONObject;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.StringUtil;

public class PlayerListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().isOp()) {
            Bukkit.getScheduler().runTaskAsynchronously(FlyToggle.getInstance(), ()-> {
                try {
                    if (new SpigotUpdater(FlyToggle.getInstance()).checkForUpdates()) {
                        event.getPlayer().sendMessage(Util.color("&c&lFlyToggle &8> &aThere is a new update available!"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        if (event.getPlayer().getName().equals("Fede1132")) {
            event.getPlayer().sendMessage(Util.color("&c&lFlyToggle &8> Hey! This server uses your plugin!"));
        }
        if (!Boolean.parseBoolean(Util.getFromConfig("Settings.Disable-Fly-On-Quit"))
                || (!Util.getFromConfig("Settings.Fly-On-Quit-Permission").equals("") && event.getPlayer().hasPermission(Util.getFromConfig("Settings.Fly-On-Quit-Permission")))) {
            Json data = FlyToggle.getInstance().data;
            if (!data.contains(event.getPlayer().getName())) {
                return;
            }
            JSONObject pData = new JSONObject(data.getMap(event.getPlayer().getName()));
            boolean status = pData.getBoolean("fly");
            event.getPlayer().setAllowFlight(status);
            if (status) event.getPlayer().setFlying(true);
            event.getPlayer().sendMessage(Util.getFromConfig("Fly." + (status ? ".Fly-Enabled" : ".Fly-Disabled")));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (!Boolean.parseBoolean(Util.getFromConfig("Settings.Disable-Fly-On-Quit"))
                || (!Util.getFromConfig("Settings.Fly-On-Quit-Permission").equals("") && event.getPlayer().hasPermission(Util.getFromConfig("Settings.Fly-On-Quit-Permission")))) {
            Json data = FlyToggle.getInstance().data;
            JSONObject pData = new JSONObject();
            if (data.contains(event.getPlayer().getName()))
                pData = new JSONObject(data.getMap(event.getPlayer().getName()));
            pData.put("fly", event.getPlayer().getAllowFlight());
            data.set(event.getPlayer().getName(), pData);
        }
    }
}
