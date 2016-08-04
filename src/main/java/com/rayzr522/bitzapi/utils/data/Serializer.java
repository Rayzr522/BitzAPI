
package com.rayzr522.bitzapi.utils.data;

import org.bukkit.Location;
import org.bukkit.World;

public class Serializer {

	public static String worldID(World world) {

		return world.getUID().toString();

	}

	public static String location(Location loc) {

		return loc.toVector().toString() + ";" + worldID(loc.getWorld());

	}

}
