
package com.rayzr522.bitzapi.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.world.Region;

public class ConfigUtils {

	private BitzPlugin plugin;

	public ConfigUtils(BitzPlugin plugin) {

		this.plugin = plugin;

	}

	public static String toConfigPath(String path) {

		return (ChatColor.stripColor(path.toLowerCase()));

	}

	public void saveRegion(ConfigurationSection section, Region region, String type) {

		ConfigurationSection regionSection = getSection(section, type + "Region");

		if (region == null) {

		return;

		}

		String formatted = region.getMin().toString() + "," + region.getMin().toString();

		regionSection.set("pos", formatted);
		saveWorld(regionSection, region.getWorld(), "world");

	}

	public Region parseRegion(ConfigurationSection section, String type) {

		if (!section.contains(type + "Region")) {

		return null;

		}

		ConfigurationSection regionSection = getSection(section, type + "Region");

		String configString = regionSection.getString("pos");

		String[] split = configString.trim().split(",");

		if (split.length < 6) {

		return null;

		}

		int x1 = Integer.parseInt(split[0]);
		int y1 = Integer.parseInt(split[1]);
		int z1 = Integer.parseInt(split[2]);

		int x2 = Integer.parseInt(split[3]);
		int y2 = Integer.parseInt(split[4]);
		int z2 = Integer.parseInt(split[5]);

		World world = parseWorld(regionSection, "world");

		return new Region(x1, y1, z1, x2, y2, z2, world);

	}

	public void saveLocation(ConfigurationSection section, Location location, String type) {

		if (location == null) {

		return;

		}

		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		World world = location.getWorld();

		String formatted = x + "," + y + "," + z + "," + world.getUID();

		section.set(type + "Location", formatted);

	}

	public Location parseLocation(ConfigurationSection section, String type) {

		if (!section.contains(type + "Location")) {

		return null;

		}

		String configString = section.getString(type + "Location");

		String[] split = configString.trim().split(",");

		if (split.length < 4) {

		return null;

		}

		double x = Double.parseDouble(split[0]);
		double y = Double.parseDouble(split[1]);
		double z = Double.parseDouble(split[2]);
		World world = Bukkit.getWorld(UUID.fromString(split[3]));

		return new Location(world, x, y, z);

	}

	public void saveBoolean(ConfigurationSection section, boolean bool, String name) {

		section.set(name, bool);

	}

	public boolean parseBoolean(ConfigurationSection section, String name) {

		if (section.contains(name)) {

		return section.getBoolean(name);

		}

		return false;

	}

	public void saveWorld(ConfigurationSection section, World world, String name) {

		section.set(name + "World", world.getUID().toString());

	}

	public World parseWorld(ConfigurationSection section, String name) {

		if (!section.contains(name + "World")) {

		return null;

		}

		return Bukkit.getWorld(UUID.fromString(section.getString(name + "World")));

	}

	public ConfigurationSection getSection(String path) {

		if (!plugin.getConfig().contains(path)) {

		return plugin.getConfig().createSection(path);

		}

		return plugin.getConfig().getConfigurationSection(path);

	}

	public ConfigurationSection getSection(ConfigurationSection section, String path) {

		if (!section.contains(path)) {

		return section.createSection(path);

		}

		return section.getConfigurationSection(path);

	}

	public void saveConfig() {

		plugin.saveConfig();

	}

	public void reloadConfig() {

		plugin.reloadConfig();

	}

	public boolean sectionExists(ConfigurationSection section, String string) {
		return section.isConfigurationSection(string);
	}

	public boolean sectionExists(String string) {
		return plugin.getConfig().isConfigurationSection(string);
	}

}
