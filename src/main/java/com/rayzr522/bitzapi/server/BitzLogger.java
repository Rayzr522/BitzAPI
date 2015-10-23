package com.rayzr522.bitzapi.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

public class BitzLogger {
	
	private Logger logger;
	private String name;
	private String ver;
	
	public BitzLogger(Plugin plugin) {
		
		this.logger = plugin.getLogger();
		this.name = plugin.getName();
		this.ver = plugin.getDescription().getVersion();
		
	}
	
	public void info(String msg) {
		
		logger.log(Level.INFO, "["+ name + "] " + msg);
		
	}
	
	public void warning(String msg) {
		
		logger.log(Level.WARNING, "["+ name + "] [WARNING] " + msg);
		
	}
	
	public void error(String msg) {
		
		logger.log(Level.SEVERE, "["+ name + "] [ERROR - SEVERE] " + msg);
		
	}
	
	public void loaded() {
		
		info(name + " v" + ver + " has been loaded!");
		
	}
	
	public void unloaded() {
		
		info(name + " v" + ver + " has been unloaded!");
		
	}

}
