
package com.rayzr522.bitzapi.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.config.data.Serializable;
import com.rayzr522.bitzapi.config.data.Serialized;
import com.rayzr522.bitzapi.utils.Reflection;
import com.rayzr522.bitzapi.utils.data.ArrayUtils;

public class ConfigManager {

	@SuppressWarnings("unused")
	private BitzPlugin	plugin;
	private File		dataFolder;

	public ConfigManager(BitzPlugin plugin) {

		this.plugin = plugin;
		this.dataFolder = plugin.getDataFolder();

		folderExists(dataFolder);

	}

	public boolean exists(File file) {

		if (!file.exists()) {
			try {
				file.createNewFile();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		return true;

	}

	public boolean folderExists(File file) {

		if (!file.exists()) {
			file.mkdir();
			return false;
		}

		return true;

	}

	/**
	 * If the config file already exists it is not overwritten
	 * 
	 * @param path
	 *            the path of the file
	 * @return A new config file
	 */
	public YamlConfiguration createConfig(String path) {

		File file = new File(dataFolder, path);
		exists(file);

		return YamlConfiguration.loadConfiguration(file);

	}

	public void saveConfig(YamlConfiguration config, File file) {
		try {
			config.save(file);
		} catch (Exception e) {
			System.err.println("Failed to save config file");
		}
	}

	public void saveConfig(YamlConfiguration config, String file) {
		try {
			createConfig(file);
			config.save(getFile(file));
		} catch (Exception e) {
			System.err.println("Failed to save config file");
		}
	}

	public void save(Object o, String path) {

		YamlConfiguration config = createConfig(path);

		save(o, config);

		saveConfig(config, path);

	}

	public void save(Object o, YamlConfiguration config) {

		Map<String, Object> map = serialize(o);

		if (map == null) { return; }

		saveToConfig(config, map);

	}

	public void save(Object o, YamlConfiguration config, String path) {

		save(o, config);

		saveConfig(config, path);

	}

	public void save(Object o, ConfigurationSection section) {

		Map<String, Object> map = serialize(o);

		if (map == null) { return; }

		for (Entry<String, Object> entry : map.entrySet()) {

			section.set(entry.getKey(), entry.getValue());

		}

	}

	public static Map<String, Object> serialize(Object o) {

		if (!Reflection.hasInterface(o, Serializable.class)) {
			System.err.println("Attempted to serialize a class that does not implement Serializable!");
			System.err.println("Invalid class: '" + o.getClass().getCanonicalName() + "'");
			System.err.println("Interfaces: " + ArrayUtils.concatArray(o.getClass().getInterfaces(), ", "));
			return null;
		}

		List<Field> fields = Reflection.getFieldsWithAnnotation(o.getClass(), Serialized.class);

		Map<String, Object> map = new HashMap<String, Object>();

		for (Field field : fields) {

			try {

				// Save the state of the field
				boolean accessible = field.isAccessible();
				field.setAccessible(true);

				// Check if it's another Serializable
				if (Reflection.hasInterface(field.getType(), Serializable.class)) {
					map.put(field.getName(), serialize(field.get(o)));
				} else {
					map.put(field.getName(), field.get(o));
				}

				field.setAccessible(accessible);

			} catch (IllegalArgumentException e) {

				e.printStackTrace();

			} catch (IllegalAccessException e) {

				e.printStackTrace();

			} catch (StackOverflowError e) {

				System.err.println("Data serializer caught in infinite loop while trying to serialize an object of type '" + o.getClass().getCanonicalName() + "'!");
				e.printStackTrace();

			}

		}

		return map;

	}

	@SuppressWarnings("unchecked")
	public static Object deserialize(Class<? extends Object> clazz, Map<String, Object> data) {

		if (!Serializable.class.isAssignableFrom(clazz)) {
			System.err.println("Attempted to deserialize to a non-serializable class!!");
			return null;
		}

		Object o;
		try {
			o = clazz.newInstance();
		} catch (Exception e) {
			System.err.println("Could not instantiate an object of type '" + clazz.getCanonicalName() + "'");
			System.err.println("Classes implementing Serializable should not have a constructor, instead they should use onDeserialized.");
			e.printStackTrace();
			return null;
		}

		List<Field> fields = Reflection.getFieldsWithAnnotation(o.getClass(), Serialized.class);;

		for (Field field : fields) {
			if (!data.containsKey(field.getName())) {
				continue;
			}
			if (Reflection.hasInterface(Serializable.class, field.getType())) {
				if (!(data.get(field.getName()) instanceof Map<?, ?>)) {
					System.err.println("Expected a Map for field '" + field.getName() + "' in '" + clazz.getCanonicalName() + "', however an instance of '" + data.get(field.getName()).getClass().getCanonicalName() + "' was found!");
					return null;
				}
				Reflection.setValue(field, o, deserialize(field.getType(), (Map<String, Object>) data.get(field.getName())));
			} else {
				Reflection.setValue(field, o, data.get(field.getName()));
			}

		}

		return o;

	}

	public File getFile(String path) {
		return new File(dataFolder + File.separator + path);
	}

	@SuppressWarnings("unchecked")
	public static boolean saveToConfig(YamlConfiguration config, Map<String, Object> map) {

		if (config == null || map == null) { return false; }

		for (Entry<String, Object> entry : map.entrySet()) {

			if (entry.getValue() != null && Map.class.isAssignableFrom(entry.getValue().getClass())) {
				saveToConfig(config.createSection(entry.getKey()), (Map<String, Object>) entry.getValue());
			} else {
				config.set(entry.getKey(), entry.getValue());
			}

		}

		return true;

	}

	@SuppressWarnings("unchecked")
	public static void saveToConfig(ConfigurationSection section, Map<String, Object> map) {

		if (section == null || map == null) { return; }

		for (Entry<String, Object> entry : map.entrySet()) {
			if (Map.class.isAssignableFrom(entry.getValue().getClass())) {
				saveToConfig(section.createSection(entry.getKey()), (Map<String, Object>) entry.getValue());
			} else {
				section.set(entry.getKey(), entry.getValue());
			}
		}

	}

}
