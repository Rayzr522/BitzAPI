
package com.rayzr522.bitzapi.config;

import java.io.File;
import java.io.IOException;

import com.rayzr522.bitzapi.BitzPlugin;

public class ConfigManager {

	@SuppressWarnings("unused")
	private BitzPlugin	plugin;
	private File		dataFolder;

	public ConfigManager(BitzPlugin plugin) {

		this.plugin = plugin;
		this.dataFolder = plugin.getDataFolder();

		exists(dataFolder);

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

	/**
	 * Do not add .yml to the end of path
	 * 
	 * @param path
	 * @return a new
	 */
	public BitzConfig createConfigFile(String path) {
		
		File file = new File(dataFolder, path + ".yml");
		exists(file);
		
		return new BitzConfig(file);

	}

}
