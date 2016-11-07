
package com.rayzr522.bitzapi.commands.bitz;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.message.BitzMessages;
import com.rayzr522.bitzapi.utils.CommandUtils;
import com.rayzr522.bitzapi.utils.world.BitzTools;
import com.rayzr522.bitzapi.utils.world.BitzTools.ToolType;

@CommandInfo(name = "tools", usage = "/{command} tools [reg:loc:locs]", desc = "Get one or all of the Bitz Tools", pattern = "tool(s)?", perm = "{base}.tools")
public class BitzCommandTools implements BitzCommand {

    public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {

        if (!CommandUtils.isPlayer(sender)) {

            plugin.messenger.playerMessage(sender, BitzMessages.ONLY_PLAYERS.msg);
            return true;

        }

        Player player = (Player) sender;

        if (args.length < 1) {

            player.getInventory().addItem(BitzTools.createTool(Material.GOLD_NUGGET, ToolType.REGION_TOOL), BitzTools.createTool(Material.STICK, ToolType.LOCATION_TOOL),
                    BitzTools.createTool(Material.BLAZE_ROD, ToolType.LOCATION_LIST_TOOL));

            plugin.messenger.playerInfo(player, "Region Tool given");
            plugin.messenger.playerInfo(player, "Location Tool given");
            plugin.messenger.playerInfo(player, "Location List Tool given");

        } else {

            ItemStack tool;

            if (args[0].equals("reg")) {

                tool = BitzTools.createTool(Material.GOLD_NUGGET, ToolType.REGION_TOOL);
                plugin.messenger.playerInfo(player, "Region Tool given");

            }

            else

            if (args[0].equals("loc")) {

                tool = BitzTools.createTool(Material.STICK, ToolType.LOCATION_TOOL);
                plugin.messenger.playerInfo(player, "Location Tool given");

            }

            else

            if (args[0].equals("locs")) {

                tool = BitzTools.createTool(Material.BLAZE_ROD, ToolType.LOCATION_LIST_TOOL);
                plugin.messenger.playerInfo(player, "Location List Tool given");

            }

            else {

                return false;

            }

            player.getInventory().addItem(tool);

        }

        return true;
    }

}
