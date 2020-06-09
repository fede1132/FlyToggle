package com.gmail.fendt873.flytoggle;

import com.gmail.fendt873.flytoggle.cmds.CmdFly;
import com.gmail.fendt873.flytoggle.cmds.CmdGiveFlyItem;
import com.gmail.fendt873.flytoggle.events.PlayerInteractListener;
import com.gmail.fendt873.flytoggle.events.PlayerListener;
import de.leonhard.storage.Json;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ConfigSettings;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public final class FlyToggle extends JavaPlugin {
    private static FlyToggle instance;
    public Yaml config;
    public Json data;
    public ArrayList<Player> use = new ArrayList<>();
    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("#> Loading FlyToggle - v" + getDescription().getVersion());
        getLogger().info("#> Settings up bStats..");
        new Metrics(this, 2635);
        getLogger().info("#> Loading plugin files...");
        config = LightningBuilder.fromFile(new File(getDataFolder(), "config.yml")).addInputStream(getResource("config.yml")).setConfigSettings(ConfigSettings.PRESERVE_COMMENTS).createYaml();
        data = LightningBuilder.fromFile(new File(getDataFolder(), "data")).createJson();
        getLogger().info("#> Loading command..");
        getCommand("fly").setExecutor(new CmdFly());
        getCommand("giveflyitem").setExecutor(new CmdGiveFlyItem());
        getLogger().info("#> Loading events..");
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new PlayerInteractListener(), this);
        manager.registerEvents(new PlayerListener(), this);
        getLogger().info("#> FlyToggle startup done!");
    }

    public static FlyToggle getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
