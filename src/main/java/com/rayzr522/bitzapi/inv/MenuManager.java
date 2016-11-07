
package com.rayzr522.bitzapi.inv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.plugin.BitzAPI;
import com.rayzr522.bitzapi.utils.ConfigUtils;
import com.rayzr522.bitzapi.utils.MathUtils;
import com.rayzr522.bitzapi.utils.TextUtils;
import com.rayzr522.bitzapi.utils.data.ListUtils;
import com.rayzr522.bitzapi.utils.item.ItemUtils;

public class MenuManager {

    public static HashMap<Integer, Integer> menus = new HashMap<Integer, Integer>();

    public static boolean isMenu(Inventory inventory) {

        return menus.containsKey(inventory.hashCode());

    }

    public static Inventory createMenu(BitzPlugin plugin, Player sender, int rows, String title) {
        return Bukkit.createInventory(new MenuHolder(plugin, Bukkit.createInventory(sender, MathUtils.clamp(rows, 1, 6) * 9, TextUtils.colorize(title))), MathUtils.clamp(rows, 1, 6) * 9,
                TextUtils.colorize(title));
    }

    public static Inventory createMenu(BitzPlugin plugin, Player sender, int rows, String title, int id) {
        Inventory inventory = createMenu(plugin, sender, rows, title);
        menus.put(inventory.hashCode(), id);
        return inventory;
    }

    public static Inventory createMenu(Player sender, BitzPlugin plugin, ConfigurationSection base) {

        if (!isInvOwnedBy(base, plugin)) {

            plugin.logger.warning("This plugin is attempting to load an inventory created by another plugin.");

        }

        int rows = base.getInt("rows");

        if (rows < 1) {
            rows = 6;
        }

        String title = base.getString("title");
        if (title == null) {
            title = "&4Bitz&6&lInv";
        }

        int id;
        if (base.contains("id")) {
            id = base.getInt("id");
        } else {
            id = UUID.randomUUID().hashCode();
        }

        Inventory output = createMenu(plugin, sender, rows, title, id);

        if (base.isConfigurationSection("items")) {

            ConfigurationSection items = base.getConfigurationSection("items");

            ItemStack defItem = ItemUtils.createItem(Material.PORK, 1, 0, "&6Bitz&4&lItem", Arrays.asList("Line1", "&7Line&62"));
            ItemMeta defMeta = defItem.getItemMeta();

            ItemStack[] contents = output.getContents();

            for (int i = 0; i < rows * 9; i++) {

                if (!items.isConfigurationSection("slot" + (i + 1))) {

                    continue;

                }

                ConfigurationSection item = items.getConfigurationSection("slot" + (i + 1));

                ItemStack itemstack;

                String typeString = item.getString("type");

                if (typeString == null) {

                    typeString = ItemUtils.toString(defItem);

                }

                if (ItemUtils.parseItem(typeString) == null) {

                    contents[i] = defItem;
                    continue;

                }

                itemstack = ItemUtils.parseItem(typeString);
                ItemMeta meta = itemstack.getItemMeta();

                String name = item.getString("name");
                if (name == null) {

                    meta.setDisplayName(defMeta.getDisplayName());

                }

                List<String> lore = item.getStringList("lore");
                meta.setDisplayName(TextUtils.colorize(name));
                meta.setLore(ListUtils.colorList(lore));
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                itemstack.setItemMeta(meta);

                contents[i] = itemstack;

            }

            output.setContents(contents);

        }

        return output;

    }

    public static boolean isInvOwnedBy(ConfigurationSection invBase, BitzPlugin plugin) {

        if (getOwner(invBase).equals("")) {

            return false;

        }

        return getOwner(invBase).equals(plugin.getName().toLowerCase());

    }

    public static void setOwner(ConfigurationSection base, BitzPlugin plugin) {

        base.set("parentPlugin", plugin.getName().toLowerCase());

    }

    public static String getOwner(ConfigurationSection base) {

        if (!base.contains("parentPlugin")) {

            base.set("parentPlugin", BitzAPI.instance.getName().toLowerCase());
            return BitzAPI.instance.getName().toLowerCase();

        }

        return base.getString("parentPlugin").toLowerCase();

    }

    public static ConfigurationSection createConfigSection(Inventory inventory, ConfigurationSection base, BitzPlugin plugin) {

        ConfigurationSection inventorySection = base.createSection(ConfigUtils.toConfigPath(inventory.getTitle()));

        String title = inventory.getTitle();
        int rows = inventory.getSize() / 9;

        inventorySection.set("title", TextUtils.uncolorize(title));
        inventorySection.set("rows", rows);

        setOwner(inventorySection, plugin);
        ConfigurationSection itemsSection = inventorySection.createSection("items");

        for (int i = 0; i < rows * 9; i++) {

            if (ItemUtils.isEmpty(inventory.getContents()[i])) {

                continue;

            }

            ConfigurationSection itemSection = itemsSection.createSection("slot" + (i + 1));
            ItemStack itemstack = inventory.getContents()[i];
            ItemMeta meta = itemstack.getItemMeta();

            itemSection.set("type", ItemUtils.toString(itemstack));
            itemSection.set("name", TextUtils.uncolorize(meta.getDisplayName()));
            itemSection.set("lore", ListUtils.reverseColorList(meta.getLore()));
            itemSection.set("events", ListUtils.<String> empty());
            itemSection.set("commands", ListUtils.<String> empty());

        }

        return inventorySection;

    }

    public static List<String> getItemStringList(ConfigurationSection invBase, int slot, String name) {

        if (invBase.isConfigurationSection("items")) {

            if (invBase.isConfigurationSection("items.slot" + (slot + 1))) {

                return invBase.getStringList("items.slot" + (slot + 1) + "." + name);

            }

            return new ArrayList<String>();

        }

        return new ArrayList<String>();

    }

    public static List<String> getItemEvents(ConfigurationSection invBase, int slot) {

        return getItemStringList(invBase, slot, "events");

    }

    public static List<String> getItemCommands(ConfigurationSection invBase, int slot) {

        return getItemStringList(invBase, slot, "commands");

    }

    public static boolean setItemStringList(ConfigurationSection invBase, int slot, String name, List<String> value) {

        if (invBase.isConfigurationSection("items")) {

            if (invBase.isConfigurationSection("items.slot" + (slot + 1))) {

                invBase.set("items.slot" + (slot + 1) + "." + name, value);
                return true;

            }

            return false;

        }

        return false;

    }

    public static boolean setItemEvents(ConfigurationSection invBase, int slot, List<String> value) {

        return setItemStringList(invBase, slot, "events", value);

    }

    public static boolean setItemCommands(ConfigurationSection invBase, int slot, List<String> value) {

        return setItemStringList(invBase, slot, "commands", value);

    }

}
