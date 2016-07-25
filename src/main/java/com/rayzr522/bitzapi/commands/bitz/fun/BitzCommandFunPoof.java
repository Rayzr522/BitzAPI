
package com.rayzr522.bitzapi.commands.bitz.fun;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.client.BitzMessages;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.utils.commands.CommandUtils;

@CommandInfo(name = "poof", usage = "/{command} fun poof", desc = "Poof!", pattern = "poof", perm = "{base}.fun")
public class BitzCommandFunPoof implements BitzCommand {

	public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {
		if (!CommandUtils.isPlayer(sender)) {

			plugin.messenger.playerMessage(sender, BitzMessages.ONLY_PLAYERS.msg);
			return true;

		}

		Player player = (Player) sender;

		World world = player.getWorld();

		Firework fw = (Firework) world.spawn(player.getLocation(), Firework.class);

		FireworkEffect effect = FireworkEffect.builder().with(Type.BURST).trail(true).withColor(Color.MAROON, Color.RED, Color.SILVER).build();
		FireworkEffect effect2 = FireworkEffect.builder().with(Type.BURST).trail(true).withColor(Color.AQUA, Color.TEAL, Color.BLUE).build();

		FireworkMeta meta = fw.getFireworkMeta();

		meta.addEffect(effect);
		meta.addEffect(effect2);

		meta.setPower(1);

		fw.setFireworkMeta(meta);

		return true;
	}

}
