
package com.rayzr522.bitzapi;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.bitzapi.commands.CommandHandler;
import com.rayzr522.bitzapi.message.BitzLogger;
import com.rayzr522.bitzapi.message.Messenger;
import com.rayzr522.bitzapi.plugin.BitzAPI;
import com.rayzr522.bitzapi.utils.ConfigUtils;

public class BitzPlugin extends JavaPlugin {

	public BitzLogger			logger;
	public Messenger			messenger;
	public ConfigUtils			configUtils;
	protected CommandHandler	commandHandler;

	/**
	 * General BitzPlugin use: <br>
	 * <code>
	 * <br>
	 * instance = this;
	 * <br>
	 * commandHandler.autoSetup();
	 * <br>
	 * registerCommands();
	 * </code>
	 */
	public void onEnable() {

		this.logger = new BitzLogger(this);
		this.messenger = new Messenger(this);
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

		if (perm.equals("")) {

			return true;

		} else if (perm.contains("{base}")) {

			return sender.hasPermission(perm.replace("{base}", commandHandler.getBasePerm()));

		} else {

			return sender.hasPermission(perm);

		}

	}

	/**
	 * @param player
	 * @param perm
	 * @return Whether player has a specified permission
	 */
	public boolean has(Player player, String perm) {

		if (perm.equals("")) {

			return true;

		} else if (perm.contains("{base}")) {

			return player.hasPermission(perm.replace("{base}", commandHandler.getBasePerm()));

		} else {

			return player.hasPermission(perm);

		}

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

	/**
	 * @return the {@code commandHandler} of this BitzPlugin
	 */
	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

}
