package com.rayzr522.bitzapi.utils.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class BitzTools {

	/**
	 * Used in {@link BitzTools.createTool}
	 */
	public static final int REGION_TOOL = 0;

	/**
	 * Used in {@link BitzTools.createTool}
	 */
	public static final int LOCATION_TOOL = 1;

	/**
	 * Used in {@link BitzTools.createTool}
	 */
	public static final int LOCATION_LIST_TOOL = 2;

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

		ItemStack item = new ItemStack(type);

		if (toolType < 0 || toolType > 2) {
			return null;
		}

		item.addUnsafeEnchantment(Enchantment.SILK_TOUCH, -333);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, -300 + toolType);

		ItemMeta im = item.getItemMeta();

		im.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);

		List<String> lore = new ArrayList<String>();

		String displayName = ChatColor.GREEN + ChatColor.BOLD.toString();

		switch (toolType) {

		case REGION_TOOL:

			displayName += "Region Tool";
			lore.add(ChatColor.RED + "Left Click: " + ChatColor.WHITE + "Set point 1");
			lore.add(ChatColor.BLUE + "Right Click: " + ChatColor.WHITE + "Set point 2");
			break;

		case LOCATION_TOOL:

			displayName += "Location Tool";
			lore.add(ChatColor.RED + "Left Click: " + ChatColor.WHITE + "Select location");
			break;

		case LOCATION_LIST_TOOL:

			displayName += "Location List Tool";
			lore.add(ChatColor.RED + "Left Click: " + ChatColor.WHITE + "Add location");
			lore.add(ChatColor.BLUE + "Right Click: " + ChatColor.WHITE + "Remove location");
			break;

		}

		im.setDisplayName(displayName);
		im.setLore(lore);

		item.setItemMeta(im);

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

		boolean isTool = false;

		for (Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {

			Enchantment enchant = entry.getKey();
			int level = entry.getValue() - 65536;

			if (enchant.getName().equals(Enchantment.SILK_TOUCH.getName()) && level == -333) {

				isTool = true;

			}

		}

		return isTool;

	}

	/**
	 * 
	 * Gets the tool type
	 * 
	 * @param item
	 * @return toolType
	 */
	public static int getToolType(ItemStack item) {

		int toolType = -1;

		if (!isTool(item)) {

			return -1;

		}

		for (Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {

			if (entry.getKey().getName().equals(Enchantment.DURABILITY.getName())) {

				toolType = entry.getValue() + 300 - 65536;

			}

		}

		return toolType;

	}

}
