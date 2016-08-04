
package com.rayzr522.bitzapi.client;

import org.bukkit.ChatColor;

public class Message {

	public String		text;
	public ChatColor	type;

	public boolean		returnType;

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
	 * @return Returns this text with all the {#} changed to {@code s}
	 */
	public String format(String... s) {

		if (s == null) { return text; }

		String out = text;

		for (int i = 0; i < s.length; i++) {

			out = out.replaceAll("{" + i + "}", s[i]);

		}

		return out;

	}

}
