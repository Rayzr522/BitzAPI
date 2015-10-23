package com.rayzr522.bitzapi.utils.world;

import org.bukkit.Location;

public class LocUtils {
	
	public static Location blockify(Location loc) {
		
		Location result = loc.clone();
		
		result.setX(result.getBlockX());
		result.setY(result.getBlockY());
		result.setZ(result.getBlockZ());
		
		return result;
		
	}
	
	public static Location centerify(Location loc) {
		
		Location result = blockify(loc);
		
		result.setX(result.getX() + 0.5);
		result.setZ(result.getZ() + 0.5);
		
		return result;
		
	}
	
	public static String toString(Location loc) {
		
		return (loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
		
	}
	
}
