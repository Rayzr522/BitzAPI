
package com.rayzr522.bitzapi.client;

import org.bukkit.ChatColor;

public class Message {

	public String		text;
	public ChatColor	type;

	public boolean returnType;

	/**
	 * @param text
	 * @param type
	 *            the {@code ChatColor} to use for this message (used in
	 *            {@link Messenger})
	 * 
	 */
	public Message(String text, ChatColor type, boolean returnType) {

		this.text = text;
		this.type = type;

		this.returnType = returnType;

	}

	/**
	 * @param s
	 *            the text to replace with
	 * @return Returns this text with all instances of "%" changed to {@code s}
	 */
	public String format(String s) {

		return s == null ? "" : this.text.replace("%", s);

	}

}
