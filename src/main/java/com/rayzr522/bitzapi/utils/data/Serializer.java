
package com.rayzr522.bitzapi.utils.data;

import org.bukkit.Location;
import org.bukkit.World;

public class Serializer {

	public static String worldId(World world) {

		return world.getUID().toString();

	}

	public static String location(Location loc) {

		return loc.toVector().toString() + ";" + worldId(loc.getWorld());

	}

}
