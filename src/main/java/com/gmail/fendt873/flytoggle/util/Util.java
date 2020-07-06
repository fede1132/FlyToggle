package com.gmail.fendt873.flytoggle.util;

import com.gmail.fendt873.flytoggle.FlyToggle;
import com.google.common.base.Charsets;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static String getFromConfig(String path) {
        return color(FlyToggle.getInstance().config.getOrDefault(path, "Invalid string found at path: " + path));
    }

    public static int getIntFromConfig(String path) {
        try {
            return Integer.parseInt(getFromConfig(path));
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    public static List<String> getListFromConfig(String path) {
        List<String> lore = new ArrayList<>(FlyToggle.getInstance().config.getStringList(path));
        for (int i=0;i<lore.size();i++) {
            String item = lore.get(i);
            lore.remove(i);
            lore.add(i, color(item));
        }
        return lore;
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', new String(s.getBytes(), Charsets.UTF_8));
    }
}
