
package com.rayzr522.bitzapi.utils.world;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.plugin.BitzAPI;
import com.rayzr522.bitzapi.utils.Pair;
import com.rayzr522.bitzapi.utils.data.LoreData;
import com.rayzr522.bitzapi.utils.item.ItemUtils;

public class BitzTools {

    private static final String BOOL_isTool     = "isBitzTool";
    private static final String STRING_toolType = "bitzToolType";

    private static final int    MIN_TOOL_TYPE   = 0;
    private static final int    MAX_TOOL_TYPE   = 2;

    /**
     * 
     * Creates a new tool
     * 
     * @param type
     *            the item material of the tool
     * @param toolType
     *            the tool type
     * @return New tool ItemStack
     */
    public static ItemStack createTool(Material type, int toolType) {

        if (toolType < MIN_TOOL_TYPE || toolType > MAX_TOOL_TYPE) {
            return null;
        }

        switch (toolType) {

            default:

                return createTool(type, ToolType.REGION_TOOL_NAME, "Set point 1", "Set point 2", true, BitzAPI.instance);

            case ToolType.LOCATION_TOOL:

                return createTool(type, ToolType.LOCATION_TOOL_NAME, "Select location", null, true, BitzAPI.instance);

            case ToolType.LOCATION_LIST_TOOL:

                return createTool(type, ToolType.LOCATION_LIST_TOOL_NAME, "Add location", "Remove location", true, BitzAPI.instance);

        }

    }

    public static ItemStack createTool(Material type, String name, String leftClick, String rightClick, boolean shiny, BitzPlugin plugin) {

        ItemStack item = new ItemStack(type, 1, (short) 0);

        ItemMeta im = item.getItemMeta();

        im.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);

        if (shiny) {
            im.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        }

        List<String> lore = new ArrayList<String>();

        String displayName = ChatColor.GREEN + "" + ChatColor.BOLD + name;

        if (leftClick != null) {
            lore.add(ChatColor.RED + "Left Click: " + ChatColor.WHITE + leftClick);
        }

        if (rightClick != null) {
            lore.add(ChatColor.BLUE + "Right Click: " + ChatColor.WHITE + rightClick);
        }

        im.setDisplayName(displayName);
        im.setLore(lore);

        item.setItemMeta(im);

        LoreData data = new LoreData(item);
        data.set(STRING_toolType, plugin.getName() + ":" + name);
        data.set(BOOL_isTool, true);
        data.write(item);

        return item;

    }

    /**
     * Returns whether item fits the BitzTools tool criteria
     * 
     * @param item
     *            the item to be tested
     * 
     */
    public static boolean isTool(ItemStack item) {

        if (ItemUtils.isEmpty(item)) {

            return false;

        }

        LoreData data = new LoreData(item);
        return data.getBool(BOOL_isTool);

    }

    /**
     * 
     * Gets the tool type
     * 
     * @param item
     * @return a {@code Pair<String, String>} formatted as
     *         {@code pluginName, toolName}
     */
    public static ToolType getToolType(ItemStack item) {

        if (!isTool(item)) {
            return null;
        }

        LoreData data = new LoreData(item);
        String type = data.getString(STRING_toolType);
        String[] split = type.split(":");

        if (split.length != 2) {
            return null;
        }

        return new ToolType(split[0], split[1]);

    }

    public static class ToolType {

        /**
         * Used in {@link BitzTools.createTool}
         */
        public static final int      REGION_TOOL             = 0;

        /**
         * Used in {@link BitzTools.createTool}
         */
        public static final int      LOCATION_TOOL           = 1;

        /**
         * Used in {@link BitzTools.createTool}
         */
        public static final int      LOCATION_LIST_TOOL      = 2;

        /**
         * Used in {@link BitzTools.createTool}
         */
        public static final String   REGION_TOOL_NAME        = "Region Tool";

        /**
         * Used in {@link BitzTools.createTool}
         */
        public static final String   LOCATION_TOOL_NAME      = "Location Tool";

        /**
         * Used in {@link BitzTools.createTool}
         */
        public static final String   LOCATION_LIST_TOOL_NAME = "Location List Tool";

        private Pair<String, String> pair;

        public ToolType(String pluginName, String name) {

            setPair(new Pair<String, String>(pluginName, name));

        }

        /**
         * @return the pair
         */
        public Pair<String, String> getPair() {
            return pair;
        }

        /**
         * @param pair
         *            the pair to set
         */
        public void setPair(Pair<String, String> pair) {
            this.pair = pair;
        }

        /**
         * @return the plugin name
         */
        public String getPluginName() {
            return pair.a();
        }

        /**
         * @return the tool type name
         */
        public String getName() {
            return pair.b();
        }

    }

}
