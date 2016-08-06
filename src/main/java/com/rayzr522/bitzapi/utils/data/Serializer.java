
package com.rayzr522.bitzapi.utils.data;

import org.bukkit.Location;
import org.bukkit.World;

public class Serializer {

	/**
	 * @param world
	 * @return the {@code world}'s UUID in String form
	 */
	public static String world(World world) {

		return world.getUID().toString();

	}

	/**
	 * @param loc
	 * @return the {@code loc} in String form
	 */
	public static String location(Location loc) {

		return loc.toVector().toString() + ";" + world(loc.getWorld());

	}

}
