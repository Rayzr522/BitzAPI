package com.rayzr522.bitzapi.utils.world;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;

import com.rayzr522.bitzapi.world.Region;

public class RegionUtils {

    public static List<Block> getFrame(Region region) {

        List<Block> result = new ArrayList<Block>();

        for (int i = region.getMinX(); i <= region.getMaxX(); i++) {
            result.add(region.getWorld().getBlockAt(i, region.getMinY(), region.getMinZ()));
            result.add(region.getWorld().getBlockAt(i, region.getMinY(), region.getMaxZ()));
            result.add(region.getWorld().getBlockAt(i, region.getMaxY(), region.getMinZ()));
            result.add(region.getWorld().getBlockAt(i, region.getMaxY(), region.getMaxZ()));
        }

        for (int j = region.getMinY(); j <= region.getMaxY(); j++) {
            result.add(region.getWorld().getBlockAt(region.getMinX(), j, region.getMinZ()));
            result.add(region.getWorld().getBlockAt(region.getMinX(), j, region.getMaxZ()));
            result.add(region.getWorld().getBlockAt(region.getMaxX(), j, region.getMinZ()));
            result.add(region.getWorld().getBlockAt(region.getMaxX(), j, region.getMaxZ()));
        }

        for (int k = region.getMinZ(); k <= region.getMaxZ(); k++) {
            result.add(region.getWorld().getBlockAt(region.getMinX(), region.getMinY(), k));
            result.add(region.getWorld().getBlockAt(region.getMinX(), region.getMaxY(), k));
            result.add(region.getWorld().getBlockAt(region.getMaxX(), region.getMinY(), k));
            result.add(region.getWorld().getBlockAt(region.getMaxX(), region.getMaxY(), k));
        }

        return result;

    }

}
