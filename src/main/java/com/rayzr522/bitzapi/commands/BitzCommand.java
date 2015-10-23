package com.rayzr522.bitzapi.commands;

import org.bukkit.command.CommandSender;

import com.rayzr522.bitzapi.BitzPlugin;

public abstract interface BitzCommand {
	
	public abstract boolean execute(CommandSender sender, String[] args, BitzPlugin plugin);

}
