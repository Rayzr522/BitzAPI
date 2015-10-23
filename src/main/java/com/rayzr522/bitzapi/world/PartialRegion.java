package com.rayzr522.bitzapi.world;

import org.bukkit.Location;

public class PartialRegion {
	
	private Location p1 = null;
	private Location p2 = null;
	
	public boolean isComplete() {
		
		return getP1() != null && getP2() != null;
		
	}
	
	public Region toRegion() {
		
		if (!isComplete()) {
			
			return null;
			
		}
		
		return new Region(p1, p2);
		
	}
	
	public static PartialRegion fromRegion(Region region) {
		
		PartialRegion partial = new PartialRegion();
		partial.setP1(new Location(region.getWorld(), region.getMinX(), region.getMinY(), region.getMinZ()));
		partial.setP2(new Location(region.getWorld(), region.getMaxX(), region.getMaxY(), region.getMaxZ()));
		
		return partial;
		
	}

	public Location getP1() {
		return p1;
	}

	public void setP1(Location p1) {
		this.p1 = p1;
	}

	public Location getP2() {
		return p2;
	}

	public void setP2(Location p2) {
		this.p2 = p2;
	}

}
