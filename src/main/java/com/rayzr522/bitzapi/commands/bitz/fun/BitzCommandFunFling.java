
package com.rayzr522.bitzapi.commands.bitz.fun;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.message.BitzMessages;
import com.rayzr522.bitzapi.utils.CommandUtils;

@CommandInfo(name = "fling", usage = "/{command} fun fling [power]", desc = "Flings you in the direction you're looking!", pattern = "whe(e(e)?)?|fling", perm = "{base}.fun")
public class BitzCommandFunFling implements BitzCommand {

    public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {

        if (!CommandUtils.isPlayer(sender)) {

            plugin.messenger.playerMessage(sender, BitzMessages.ONLY_PLAYERS.msg);
            return true;

        }

        int multiplier = 2;

        try {
            multiplier = Integer.parseInt(args[0]);
        } catch (Exception e) {

        }

        Player player = (Player) sender;

        player.setVelocity(player.getVelocity().multiply(multiplier));

        return true;
    }

}
