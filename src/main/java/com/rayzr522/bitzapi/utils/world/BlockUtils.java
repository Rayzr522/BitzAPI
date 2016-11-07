
package com.rayzr522.bitzapi.utils.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import com.rayzr522.bitzapi.BitzPlugin;

public class BlockUtils {

    public static void showGhostBlocks(final List<Block> blocks, final Player player, final Material ghostMat, final Byte metadata, int duration, final BitzPlugin plugin) {

        final HashMap<Location, BlockState> oldStates = new HashMap<Location, BlockState>();

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

            @SuppressWarnings("deprecation")
            public void run() {

                for (Block b : blocks) {

                    oldStates.put(b.getLocation(), b.getState());
                    player.sendBlockChange(b.getLocation(), ghostMat, metadata);

                }

            }

        }, 0L);

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

            @SuppressWarnings("deprecation")
            public void run() {

                for (Entry<Location, BlockState> entry : oldStates.entrySet()) {

                    player.sendBlockChange(entry.getKey(), entry.getValue().getType(), entry.getValue().getRawData());

                }

            }

        }, duration * 20);

    }

    public static void showGhostBlock(Block block, Player player, Material ghostMat, Byte metadata, int duration, BitzPlugin plugin) {

        List<Block> blocks = new ArrayList<Block>();

        blocks.add(block);

        showGhostBlocks(blocks, player, ghostMat, metadata, duration, plugin);

    }

}
