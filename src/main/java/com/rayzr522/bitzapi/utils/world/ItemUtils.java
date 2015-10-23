package com.rayzr522.bitzapi.utils.world;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rayzr522.bitzapi.utils.commands.TextUtils;
import com.rayzr522.bitzapi.utils.data.ListUtils;

public class ItemUtils {

	public static boolean isEmpty(ItemStack item) {

		return item == null || item.getType() == Material.AIR;

	}

	public static ItemStack createItem(Material type, int amount, int damage, String name, List<String> lore) {

		ItemStack item = new ItemStack(type, amount, (short) damage);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(ListUtils.colorList(lore));
		item.setItemMeta(meta);
		return item;

	}

	public static ItemStack parseItem(String string) {

		String[] split = string.split(":");
		Material type = Material.valueOf(split[0].toUpperCase());

		if (type == null) {

			return null;

		}

		if (split.length == 2) {

			try {

				int damage = Integer.parseInt(split[1]);
				return new ItemStack(type, 1, (short) damage);

			} catch (Exception e) {

				return new ItemStack(type);

			}

		} else if (split.length == 3) {

			try {

				int damage = Integer.parseInt(split[1]);
				int amount = Integer.parseInt(split[2]);
				return new ItemStack(type, amount, (short) damage);

			} catch (Exception e) {

				return new ItemStack(type);

			}

		} else {

			return new ItemStack(type);

		}

	}

	public static String toString(ItemStack item) {

		String out = "";

		out += item.getType().toString();
		out += ":" + item.getDurability();
		out += ":" + item.getAmount();

		return out;

	}
	
	public static void setName(ItemStack item, String name){
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(TextUtils.color(name));
		item.setItemMeta(meta);
		
	}

}
