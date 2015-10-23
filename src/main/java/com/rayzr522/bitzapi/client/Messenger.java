
package com.rayzr522.bitzapi.client;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.bitzapi.client.Message;
import com.rayzr522.bitzapi.utils.commands.TextUtils;

public class Messenger {

	public static ChatColor INFO = ChatColor.WHITE;
	public static ChatColor NOTE = ChatColor.GRAY;
	public static ChatColor WARNING = ChatColor.YELLOW;
	public static ChatColor ERROR = ChatColor.RED;
	public static ChatColor TITLE = ChatColor.GOLD;
	private String name;
	private String prefix;
	
	public Messenger(String name) {
		
		this.name = name;
		this.prefix = "&4&l(" + this.name + ") &f";
		
	}
	
	private String buildMessage(ChatColor color, String message) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(TextUtils.color(prefix));
		
		builder.append(color + TextUtils.color(message));
		
		return builder.toString();
		
	}

	public void playerInfo(Player player, String message) {

		player.sendMessage(buildMessage(INFO, message));

	}

	public void playerNote(Player player, String message) {

		player.sendMessage(buildMessage(NOTE, message));

	}

	public void playerWarning(Player player, String message) {

		player.sendMessage(buildMessage(WARNING, message));

	}

	public void playerError(Player player, String message) {

		player.sendMessage(buildMessage(ERROR, message));

	}

	public void playerRaw(Player player, String message) {

		player.sendMessage(message);

	}

	public void playerInfo(CommandSender sender, String message) {

		if (isConsole(sender)) {
			return;
		}

		playerInfo((Player) sender, message);

	}

	public void playerNote(CommandSender sender, String message) {

		if (isConsole(sender)) {
			return;
		}

		playerNote((Player) sender, message);

	}

	public void playerWarning(CommandSender sender, String message) {

		if (isConsole(sender)) {
			return;
		}

		playerWarning((Player) sender, message);

	}

	public void playerError(CommandSender sender, String message) {

		if (isConsole(sender)) {
			return;
		}

		playerError((Player) sender, message);

	}

	public void playerRaw(CommandSender sender, String message) {

		if (isConsole(sender)) {
			return;
		}

		playerRaw((Player) sender, message);

	}

	public void playerMessage(Player player, Message message, String arg) {

		playerRaw(player, message.type + message.format(arg));

	}

	public void playerMessage(CommandSender sender, Message message, String arg) {

		if (isConsole(sender)) {
			return;
		}

		playerMessage((Player) sender, message, arg);

	}

	public void playerMessage(Player player, Message message) {

		playerRaw(player, buildMessage(message.type, message.text));

	}

	public void playerMessage(CommandSender sender, Message message) {

		if (isConsole(sender)) {
			return;
		}

		playerMessage((Player) sender, message);

	}

	public void playerTitle(Player player, String message) {

		player.sendMessage(buildMessage(TITLE, message));

	}

	public void playerTitle(CommandSender sender, String message) {

		if (isConsole(sender)) {
			return;
		}

		playerTitle((Player) sender, message);

	}

	public boolean isConsole(CommandSender sender) {

		return (sender instanceof ConsoleCommandSender);

	}

	public String getPrefix() {
		return prefix.replace(this.name, "*name*");
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix.replace("*name*", this.name);
	}

}
