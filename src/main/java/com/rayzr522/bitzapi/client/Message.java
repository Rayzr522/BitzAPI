package com.rayzr522.bitzapi.client;

import org.bukkit.ChatColor;

public class Message {
	
	public String text;
	public ChatColor type;
	
	public Message(String text, ChatColor type) {
		
		this.text = text;
		this.type = type;
		
	}

	public String format(String s) {
		
		return s == null ? "" : this.text.replace("%", s);
		
	}

}
