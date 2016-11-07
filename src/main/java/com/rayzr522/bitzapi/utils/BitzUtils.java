package com.rayzr522.bitzapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BitzUtils {

    public static Plugin getPlugin(String name) {

        return Bukkit.getPluginManager().getPlugin(name);

    }

}
