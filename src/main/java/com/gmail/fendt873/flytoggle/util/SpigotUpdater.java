package com.gmail.fendt873.flytoggle.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.plugin.java.JavaPlugin;

/*
 * @author iShadey
 * @website http://ishadey.ga/
 *
 * Class created to check updates using SpigotMC's legacy API.
 *
 */

public class SpigotUpdater {

    private final int project = 46634;
    private URL checkURL;
    private String newVersion = "";
    private final JavaPlugin plugin;

    public SpigotUpdater(JavaPlugin plugin) {
        this.plugin = plugin;
        this.newVersion = plugin.getDescription().getVersion();
        try {
            this.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + project);
        } catch (MalformedURLException e) {
        }
    }

    public int getProjectID() {
        return project;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public String getLatestVersion() {
        return newVersion;
    }

    public String getResourceURL() {
        return "https://www.spigotmc.org/resources/" + project;
    }

    public boolean checkForUpdates() throws Exception {
        URLConnection con = checkURL.openConnection();
        this.newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        return !plugin.getDescription().getVersion().equals(newVersion);
    }

}