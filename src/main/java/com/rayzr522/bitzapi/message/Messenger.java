
package com.rayzr522.bitzapi.message;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.Player;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.utils.TextUtils;

/**
 * @author Rayzr522
 *
 */
public class Messenger {

	public static final ChatColor	INFO	= ChatColor.WHITE;
	public static final ChatColor	NOTE	= ChatColor.GRAY;
	public static final ChatColor	WARNING	= ChatColor.YELLOW;
	public static final ChatColor	ERROR	= ChatColor.RED;
	public static final ChatColor	TITLE	= ChatColor.GOLD;

	private ChatColor				info	= INFO;
	private ChatColor				note	= NOTE;
	private ChatColor				warning	= WARNING;
	private ChatColor				error	= ERROR;
	private ChatColor				title	= TITLE;

	@SuppressWarnings("unused")
	private BitzPlugin				plugin;
	private String					name;
	private String					prefix;

	/**
	 * @param plugin
	 *            the owning plugin
	 */
	public Messenger(BitzPlugin plugin) {

		this.plugin = plugin;
		this.name = plugin.getDescription().getName();
		this.prefix = "&4&l(" + this.name + ")";

	}

	/**
	 * @param color
	 * @param message
	 * @return Converts all strings with "&" color codes to the
	 *         {@code ChatColor.COLOR_CHAR} and prepends the message with the
	 *         prefix specified in the instance of {@code Messenger}
	 */
	private String buildMessage(ChatColor color, String message) {

		StringBuilder builder = new StringBuilder();

		builder.append(TextUtils.colorize(prefix + " &f"));

		builder.append(color + TextUtils.colorize(message));

		return builder.toString();

	}

	/**
	 * 
	 * Uses no ChatColor
	 * 
	 * @param player
	 *            player to send the message to
	 * @param message
	 *            message to send
	 */
	public void playerRaw(Player player, String message) {

		player.sendMessage(message);

	}

	/**
	 * 
	 * Uses the {@code ChatColor} specified for this {@code Messenger} instance
	 * 
	 * @param player
	 *            player to send the message to
	 * @param message
	 *            message to send
	 */
	public void playerInfo(Player player, String message) {

		playerRaw(player, buildMessage(info, message));

	}

	/**
	 * 
	 * Uses the {@code ChatColor} specified for this {@code Messenger} instance
	 * 
	 * @param player
	 *            player to send the message to
	 * @param message
	 *            message to send
	 */
	public void playerNote(Player player, String message) {

		playerRaw(player, buildMessage(note, message));

	}

	/**
	 * 
	 * Uses the {@code ChatColor} specified for this {@code Messenger} instance
	 * 
	 * @param player
	 *            player to send the message to
	 * @param message
	 *            message to send
	 */
	public void playerWarning(Player player, String message) {

		playerRaw(player, buildMessage(warning, message));

	}

	/**
	 * 
	 * Uses the {@code ChatColor} specified for this {@code Messenger} instance
	 * 
	 * @param player
	 *            player to send the message to
	 * @param message
	 *            message to send
	 */
	public void playerError(Player player, String message) {

		playerRaw(player, buildMessage(error, message));

	}

	/**
	 * 
	 * Uses the {@code ChatColor} specified for this {@code Messenger} instance
	 * 
	 * @param player
	 *            player to send the message to
	 * @param message
	 *            message to send
	 */
	public void playerTitle(Player player, String message) {

		player.sendMessage(buildMessage(title, message));

	}

	/**
	 * 
	 * Uses the {@code ChatColor} and text specified by {@code message}, and
	 * formats it with {@code arg}
	 * 
	 * @param player
	 *            player to send the message to
	 * @param message
	 *            message to send
	 */
	public boolean playerMessage(Player player, Message message, String arg) {

		playerRaw(player, buildMessage(message.type, message.format(arg)));
		return message.returnType;

	}

	/**
	 * 
	 * Uses the {@code ChatColor} and text specified by {@code message}
	 * 
	 * @param player
	 *            player to send the message to
	 * @param message
	 *            message to send
	 */
	public boolean playerMessage(Player player, Message message) {

		playerRaw(player, buildMessage(message.type, message.text));
		return message.returnType;

	}

	/**
	 * 
	 * Uses no ChatColor
	 * 
	 * @param sender
	 *            sender to send the message to
	 * @param message
	 *            message to send
	 */
	public void playerRaw(CommandSender sender, String message) {

		sender.sendMessage(message);

	}

	/**
	 * 
	 * Uses the {@code ChatColor} specified for this {@code Messenger} instance
	 * 
	 * @param sender
	 *            sender to send the message to
	 * @param message
	 *            message to send
	 */
	public void playerInfo(CommandSender sender, String message) {

		playerRaw(sender, buildMessage(info, message));

	}

	/**
	 * 
	 * Uses the {@code ChatColor} specified for this {@code Messenger} instance
	 * 
	 * @param sender
	 *            sender to send the message to
	 * @param message
	 *            message to send
	 */
	public void playerNote(CommandSender sender, String message) {

		playerRaw(sender, buildMessage(note, message));

	}

	/**
	 * 
	 * Uses the {@code ChatColor} specified for this {@code Messenger} instance
	 * 
	 * @param sender
	 *            sender to send the message to
	 * @param message
	 *            message to send
	 */
	public void playerWarning(CommandSender sender, String message) {

		playerRaw(sender, buildMessage(warning, message));

	}

	/**
	 * 
	 * Uses the {@code ChatColor} specified for this {@code Messenger} instance
	 * 
	 * @param sender
	 *            sender to send the message to
	 * @param message
	 *            message to send
	 */
	public void playerError(CommandSender sender, String message) {

		playerRaw(sender, buildMessage(error, message));

	}

	/**
	 * 
	 * Uses the {@code ChatColor} specified for this {@code Messenger} instance
	 * 
	 * @param sender
	 *            sender to send the message to
	 * @param message
	 *            message to send
	 */
	public void playerTitle(CommandSender sender, String message) {

		if (!canRecieveMessage(sender)) { return; }

		playerTitle((Player) sender, message);

	}

	/**
	 * 
	 * Uses the {@code ChatColor} and text specified by {@code message}
	 * 
	 * @param sender
	 *            sender to send the message to
	 * @param message
	 *            message to send
	 */
	public boolean playerMessage(CommandSender sender, Message message, String arg) {

		playerRaw(sender, buildMessage(message.type, message.format(arg)));
		return message.returnType;

	}

	/**
	 * 
	 * Uses the {@code ChatColor} and text specified by {@code message}
	 * 
	 * @param player
	 *            player to send the message to
	 * @param message
	 *            message to send
	 */
	public boolean playerMessage(CommandSender sender, Message message) {

		playerRaw(sender, buildMessage(message.type, message.text));
		return message.returnType;

	}

	/**
	 * @param sender
	 *            the CommandSender
	 * @return checks to see if the sender can receive a message
	 */
	public boolean canRecieveMessage(CommandSender sender) {

		return (sender instanceof Conversable) ? !((Conversable) sender).isConversing() : true;

	}

	public String getPrefix() {
		return prefix.replace(this.name, "{name}");
	}

	/**
	 * Use "*name*" to replace it with the plugin name (useful for generic code)
	 * 
	 * @param prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix.replace("{name}", this.name);
	}

	/**
	 * @return the info color
	 */
	public ChatColor getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            the info color to set
	 */
	public void setInfo(ChatColor info) {
		this.info = info;
	}

	/**
	 * @return the note color
	 */
	public ChatColor getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note color to set
	 */
	public void setNote(ChatColor note) {
		this.note = note;
	}

	/**
	 * @return the warning color
	 */
	public ChatColor getWarning() {
		return warning;
	}

	/**
	 * @param warning
	 *            the warning color to set
	 */
	public void setWarning(ChatColor warning) {
		this.warning = warning;
	}

	/**
	 * @return the error color
	 */
	public ChatColor getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error color to set
	 */
	public void setError(ChatColor error) {
		this.error = error;
	}

	/**
	 * @return the title color
	 */
	public ChatColor getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title color to set
	 */
	public void setTitle(ChatColor title) {
		this.title = title;
	}

}
