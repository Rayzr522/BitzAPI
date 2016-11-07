
package com.rayzr522.bitzapi.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import com.rayzr522.bitzapi.config.DEPRECATED_Serializable;
import com.rayzr522.bitzapi.utils.data.Deserializer;
import com.rayzr522.bitzapi.utils.data.MapUtils;
import com.rayzr522.bitzapi.utils.data.Serializer;

public class Region implements DEPRECATED_Serializable<Region> {

    private Vector min;
    private Vector max;

    private World  world;

    public Region(int x1, int y1, int z1, int x2, int y2, int z2, World world) {

        this(new Vector(x1, y1, z1), new Vector(x2, y2, z2), world);

    }

    public Region(Location l1, Location l2) {

        this(l1.toVector(), l2.toVector(), l1.getWorld());

    }

    public Region(Vector v1, Vector v2, World world) {

        this.min = v1;
        this.max = v2;

        this.world = world;

        evalMinMax();

    }

    private void evalMinMax() {

        Vector lMin = min;
        Vector lMax = max;

        this.min = Vector.getMinimum(lMin, lMax);
        this.max = Vector.getMaximum(lMin, lMax);

    }

    public Vector getMin() {
        return min;
    }

    public Vector getMax() {
        return min;
    }

    public void setMin(Vector min) {
        this.min = min;
        evalMinMax();
    }

    public void setMax(Vector max) {
        this.max = max;
        evalMinMax();
    }

    public Location getMinLoc() {
        return min.toLocation(world);
    }

    public Location getMaxLoc() {
        return max.toLocation(world);
    }

    public void setMinLoc(Location min) {
        this.min = min.toVector();
        this.world = min.getWorld();
        evalMinMax();
    }

    public void setMaxLoc(Location max) {
        this.max = max.toVector();
        this.world = max.getWorld();
        evalMinMax();
    }

    public int getMinX() {
        return (int) min.getX();
    }

    public int getMinY() {
        return (int) min.getY();
    }

    public int getMinZ() {
        return (int) min.getZ();
    }

    public int getMaxX() {
        return (int) max.getX();
    }

    public int getMaxY() {
        return (int) max.getY();
    }

    public int getMaxZ() {
        return (int) max.getZ();
    }

    public void setMinX(double x) {
        min.setX(x);
        evalMinMax();
    }

    public void setMinY(double y) {
        min.setY(y);
        evalMinMax();
    }

    public void setMinZ(double z) {
        min.setZ(z);
        evalMinMax();
    }

    public void setMaxX(double x) {
        max.setX(x);
        evalMinMax();
    }

    public void setMaxY(double y) {
        max.setY(y);
        evalMinMax();
    }

    public void setMaxZ(double z) {
        max.setZ(z);
        evalMinMax();
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public List<Block> getBlocks() {

        List<Block> blocks = new ArrayList<Block>();

        for (int x = getMinX(); x <= getMaxX(); x++) {

            for (int y = getMinY(); y <= getMaxY(); y++) {

                for (int z = getMinZ(); z <= getMaxZ(); z++) {

                    blocks.add(world.getBlockAt(x, y, z));

                }

            }

        }

        return blocks;

    }

    public boolean inside(Vector vec) {

        int x = vec.getBlockX();
        int y = vec.getBlockY();
        int z = vec.getBlockZ();

        return inside(x, y, z);

    }

    public boolean inside(Location loc) {

        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        return inside(x, y, z);

    }

    public boolean inside(Block block) {

        Location loc = block.getLocation();

        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        return inside(x, y, z);

    }

    public boolean inside(int x, int y, int z) {

        boolean xOk = (x >= getMinX() && x <= getMaxX());
        boolean yOk = (y >= getMinY() && y <= getMaxY());
        boolean zOk = (z >= getMinZ() && z <= getMaxZ());

        boolean ok = (xOk && yOk && zOk);

        return ok;

    }

    public Map<String, Object> serialize() {

        Map<String, Object> serialized = MapUtils.empty();

        serialized.put("min", getMin().toString());
        serialized.put("max", getMax().toString());
        serialized.put("world", Serializer.world(world));

        return serialized;
    }

    public Region deserialize(Map<String, Object> serialized) {

        Vector min = Deserializer.vector((String) serialized.get("min"));
        Vector max = Deserializer.vector((String) serialized.get("max"));
        World world = Deserializer.world((String) serialized.get("world"));

        return new Region(min, max, world);
    }

}