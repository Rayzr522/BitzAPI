
package com.rayzr522.bitzapi.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class BitzConfig {

	private FileConfiguration config;

	public BitzConfig(FileConfiguration config) {

		if (config == null) { throw new IllegalArgumentException("param config cannot be null!"); }

		this.config = config;

	}

	public BitzConfig(File file) {

		if (file == null) { throw new IllegalArgumentException("param file cannot be null!"); }

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		do {
			file.exists();
		} while (!file.exists());

		config = YamlConfiguration.loadConfiguration(file);

	}

	public FileConfiguration getFileConfiguration() {
		
		return config;

	}

}
