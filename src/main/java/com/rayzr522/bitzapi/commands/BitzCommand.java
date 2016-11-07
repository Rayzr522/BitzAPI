
package com.rayzr522.bitzapi.commands;

import org.bukkit.command.CommandSender;

import com.rayzr522.bitzapi.BitzPlugin;

/**
 * Classes implementing this require the @CommandInfo annotation
 * 
 * @author Rayzr522
 * @see CommandInfo
 *
 */
public abstract interface BitzCommand {

    /**
     * If this is a player-only command use:
     * <code><blockquote>
     * if(!CommandUtils.isPlayer(sender)) {<br>
     * plugin.messenger.playerMessage(sender, BitzMessages.ONLY_PLAYERS.msg);<br>
     * 	return true;<br>
     * }
     * </blockquote></code>
     * 
     * @see CommandHandler
     */
    public abstract boolean execute(CommandSender sender, String[] args, BitzPlugin plugin);

}
