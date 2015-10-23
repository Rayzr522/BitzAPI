package com.rayzr522.bitzapi;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.bitzapi.client.Messenger;
import com.rayzr522.bitzapi.commands.CommandHandler;
import com.rayzr522.bitzapi.server.BitzLogger;
import com.rayzr522.bitzapi.utils.ConfigUtils;

public class BitzPlugin extends JavaPlugin {
	
	public BitzLogger logger;
	public Messenger messenger;
	public ConfigUtils configUtils;
	protected CommandHandler commandHandler;
	
	public void onEnable() {
		
		this.logger = new BitzLogger(this);
		this.messenger = new Messenger(this.getName());
		this.configUtils = new ConfigUtils(this);
		this.commandHandler = new CommandHandler(this, false);
		
		logger.loaded();
		
	}
	

	public void onDisable() {
		
		logger.unloaded();
		
	}
	
	/**
	 * @param player
	 * @param perm
	 * @return Whether player has a specified permission
	 */
	public boolean has(CommandSender sender, String perm) {
		
		return perm.equals("") || sender.hasPermission(perm);
		
	}
	
	/**
	 * @param player
	 * @param perm
	 * @return Whether player has a specified permission
	 */
	public boolean has(Player player, String perm) {
		
		return perm.equals("") || player.hasPermission(perm);
		
	}
	
	/**
	 * @return BitzAPI version
	 */
	public String getBitzVersion() {
		
		return BitzAPI.instance.getBitzVersion();
		
	}
	
	
	/**
	 * @return Plugin version
	 */
	public String getVersion() {
		
		return getDescription().getVersion();
		
	}
	
	/**
	 * Registers an event listener
	 * 
	 * @param listener
	 */
	public void registerEventHandler(Listener listener) {
		
		getServer().getPluginManager().registerEvents(listener, this);
		
	}

}
