package com.rayzr522.bitzapi.utils.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.rayzr522.bitzapi.world.PartialRegion;

public class BitzData {

	public static final HashMap<UUID, PartialRegion> regionSelections = new HashMap<UUID, PartialRegion>();
	public static final HashMap<UUID, Location> locationSelections = new HashMap<UUID, Location>();
	public static final HashMap<UUID, List<Location>> locationListSelections = new HashMap<UUID, List<Location>>();

	public static PartialRegion getRegionSelection(UUID playerId) {

		if (!regionSelections.containsKey(playerId) || regionSelections.get(playerId) == null) {

			regionSelections.put(playerId, new PartialRegion());

		}

		return regionSelections.get(playerId);

	}

	public static Location getLocationSelection(UUID playerId) {

		return locationSelections.get(playerId);

	}

	public static List<Location> getLocationListSelection(UUID playerId) {

		if (!locationListSelections.containsKey(playerId) || locationListSelections.get(playerId) == null) {

			locationListSelections.put(playerId, new ArrayList<Location>());

		}

		return locationListSelections.get(playerId);

	}

	public static PartialRegion getRegionSelection(Player player) {

		return getRegionSelection(player.getUniqueId());

	}

	public static Location getLocationSelection(Player player) {

		return getLocationSelection(player.getUniqueId());

	}

	public static List<Location> getLocationListSelection(Player player) {

		return getLocationListSelection(player.getUniqueId());

	}

	public static void setRegionSelection(UUID playerId, PartialRegion region) {

		regionSelections.put(playerId, region);

	}

	public static void setLocationSelection(UUID playerId, Location location) {

		locationSelections.put(playerId, location);

	}

	public static void setLocationListSelection(UUID playerId, List<Location> locationList) {

		locationListSelections.put(playerId, locationList);

	}
	
	public static void setRegionSelection(Player player, PartialRegion region) {

		setRegionSelection(player.getUniqueId(), region);

	}

	public static void setLocationSelection(Player player, Location location) {

		setLocationSelection(player.getUniqueId(), location);

	}

	public static void setLocationListSelection(Player player, List<Location> locationList) {

		setLocationListSelection(player.getUniqueId(), locationList);

	}

}
