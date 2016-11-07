
package com.rayzr522.bitzapi.commands.bitz;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.inv.MenuManager;
import com.rayzr522.bitzapi.message.BitzMessages;
import com.rayzr522.bitzapi.utils.CommandUtils;
import com.rayzr522.bitzapi.utils.ConfigUtils;
import com.rayzr522.bitzapi.utils.TextUtils;

@CommandInfo(name = "invcreate", usage = "/{command} invcreate [name]", desc = "Create an inventory, with an optional custom name", pattern = "inv(c(reate)?)?", perm = "{base}.inv.create")
public class BitzCommandCreateInv implements BitzCommand {

    public static HashMap<Integer, Inventory> creationIds = new HashMap<Integer, Inventory>();

    public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {

        if (!CommandUtils.isPlayer(sender)) {

            plugin.messenger.playerMessage(sender, BitzMessages.ONLY_PLAYERS.msg);
            return true;

        }

        Player player = (Player) sender;

        String name = "&6Bitz&4&lInv";

        if (args.length > 0) {

            name = args[0];

        }

        name = TextUtils.colorize(name);

        Inventory inventory;

        if (plugin.configUtils.getSection("createdInvs").isConfigurationSection(ConfigUtils.toConfigPath(name))) {

            inventory = MenuManager.createMenu(player, plugin, plugin.configUtils.getSection("createdInvs." + ConfigUtils.toConfigPath(name)));

        } else {

            inventory = MenuManager.createMenu(plugin, player, 6, name, UUID.randomUUID().hashCode());

        }

        player.openInventory(inventory);

        creationIds.put(inventory.hashCode(), inventory);

        return true;
    }

    public static boolean isCreationInv(Inventory inv) {

        return creationIds.containsKey(inv.hashCode());

    }

    public static Inventory removeCreationInv(Inventory inv) {

        return creationIds.remove(inv.hashCode());

    }

}
