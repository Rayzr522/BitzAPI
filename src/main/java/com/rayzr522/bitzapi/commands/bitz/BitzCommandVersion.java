
package com.rayzr522.bitzapi.commands.bitz;

import org.bukkit.command.CommandSender;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandInfo;

@CommandInfo(name = "version", desc = "Plugin info", usage = "/{command} version", pattern = "ver(sion)?|info", perm = "{base}.version")
public class BitzCommandVersion implements BitzCommand {

    public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {

        plugin.messenger.playerInfo(sender, "You are running BitzAPI v" + plugin.getVersion());
        return true;

    }

}
