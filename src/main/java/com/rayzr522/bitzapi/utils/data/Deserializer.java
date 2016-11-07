
package com.rayzr522.bitzapi.utils.data;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Deserializer {

    public static World world(String id) {

        try {
            UUID uuid = UUID.fromString(id);
            return Bukkit.getWorld(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }

    }

    public static Vector vector(String vector) {

        if (vector == null) {
            return null;
        }

        String[] split = vector.split(",");

        if (split.length < 3) {
            return null;
        }

        double x = Double.parseDouble(split[0]);
        double y = Double.parseDouble(split[1]);
        double z = Double.parseDouble(split[2]);

        return new Vector(x, y, z);

    }

    public static boolean bool(String bool) {

        return Boolean.parseBoolean(bool);

    }

}
