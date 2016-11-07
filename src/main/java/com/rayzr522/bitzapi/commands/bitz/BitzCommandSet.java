
package com.rayzr522.bitzapi.commands.bitz;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.message.BitzMessages;
import com.rayzr522.bitzapi.utils.CommandUtils;
import com.rayzr522.bitzapi.utils.data.BitzData;
import com.rayzr522.bitzapi.world.PartialRegion;
import com.rayzr522.bitzapi.world.Region;

@CommandInfo(name = "set", usage = "/{command} set <block>", desc = "Sets the selected region to <block>", pattern = "set", perm = "bitzapi.set")
public class BitzCommandSet implements BitzCommand {

    @SuppressWarnings("deprecation")
    public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {

        if (!CommandUtils.isPlayer(sender)) {

            plugin.messenger.playerMessage(sender, BitzMessages.ONLY_PLAYERS.msg);
            return true;

        }

        Player player = (Player) sender;

        if (args.length < 1) {

            plugin.messenger.playerMessage(player, BitzMessages.NO_ARG.msg, "block");
            return false;

        }

        String type = args[0].toUpperCase();
        byte meta = 0;

        if (args[0].split("[:]").length == 2) {

            String[] split = args[0].split("[:]");
            type = split[0].toUpperCase();
            try {
                meta = Byte.parseByte(split[1]);
            } catch (Exception e) {

                if (e.getClass().getName().equals("java.lang.NumberFormatException")) {

                    plugin.messenger.playerError(player, "You can't specificy a metadata of greater than 127!");
                    meta = 0;

                }

            }

        }

        Material mat = Material.getMaterial(type);

        if (mat == null) {

            plugin.messenger.playerError(player, "The block \"" + type.toLowerCase() + "\" doesn't exist!");
            return false;

        }

        PartialRegion partial = BitzData.getRegionSelection(player);
        if (!partial.isComplete()) {

            plugin.messenger.playerError(player, "You have to make a complete selection with the region tool. Use '/bitz tools' or '/bitz tools reg' to get the region tool.");
            return true;

        }

        Region region = partial.toRegion();
        for (Block block : region.getBlocks()) {

            block.setType(mat);
            block.setData(meta);

        }

        plugin.messenger.playerInfo(player, "Region set to " + args[0].toLowerCase());

        return true;
    }

}
