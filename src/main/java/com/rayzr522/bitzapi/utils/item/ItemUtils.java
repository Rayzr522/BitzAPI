
package com.rayzr522.bitzapi.utils.item;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rayzr522.bitzapi.utils.TextUtils;
import com.rayzr522.bitzapi.utils.data.ListUtils;

public class ItemUtils {

	private static List<ParseCommand>	parsers	= Arrays.asList(new ParseEnchantment(), new ParseName());

	public static final ItemStack		ERROR	= new ItemStack(Material.BARRIER, 0);

	public static ItemStack enchantItem(ItemStack base, Enchant... enchantments) {

		for (Enchant ench : enchantments) {

			base.addEnchantment(ench.getType(), ench.getLevel());

		}

		return base;

	}

	public static class Enchant {

		private int			level;
		private Enchantment	type;

		public Enchant(Enchantment type, int level) {
			this.level = level;
			this.type = type;
		}

		public int getLevel() {
			return level;
		}

		public Enchantment getType() {
			return type;
		}

	}

	public static ItemStack makeItem(String description) {

		String[] statements = description.split(",");

		if (statements.length < 1) {

			System.out.println("Requires more words");
			return ERROR;

		}

		String typeString = statements[0].trim();

		int amount = 0;

		try {
			amount = Integer.parseInt(typeString.split(" ")[0]);
			typeString = typeString.replaceFirst("^[0-9]+", "");
		} catch (Exception e) {
			amount = 1;
		}

		typeString = typeString.trim();

		Material type = null;

		try {

			type = Material.valueOf(typeString.replace(" ", "_").toUpperCase());

		} catch (Exception e) {

			System.out.println("Invalid type '" + typeString + "'");
			return ERROR;

		}

		ItemStack output = new ItemStack(type, amount);

		for (int i = 1; i < statements.length; i++) {

			String statement = statements[i].trim();

			for (ParseCommand cmd : parsers) {

				for (String str : cmd.getDescriptors()) {

					if (statement.startsWith(str)) {

						output = cmd.apply(output, statement.replaceFirst(str, "").trim());
						break;

					}

				}

			}

		}

		return output;

	}

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

	public static void setName(ItemStack item, String name) {

		if (isEmpty(item)) { return; }

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(TextUtils.colorize(name));
		item.setItemMeta(meta);

	}

	public static void setLore(ItemStack item, List<String> lore) {

		if (isEmpty(item)) { return; }

		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);

	}

	public static List<String> getLore(ItemStack item) {

		if (isEmpty(item)) { return null; }

		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore()) { return null; }

		return meta.getLore();

	}

	public static List<String> addLore(ItemStack item, List<String> lore) {

		if (isEmpty(item)) { return null; }
		if (ListUtils.isEmpty(lore)) { return null; }

		List<String> currentLore = getLore(item);
		if (currentLore == null) { return null; }
		currentLore.addAll(lore);

		ItemMeta meta = item.getItemMeta();
		meta.setLore(currentLore);
		item.setItemMeta(meta);

		return currentLore;

	}

	public static void clearLore(ItemStack item) {

		if (isEmpty(item)) { return; }

		ItemMeta meta = item.getItemMeta();
		meta.setLore(null);
		item.setItemMeta(meta);

	}

	public static Material getType(String material) {

		try {

			return Material.valueOf(TextUtils.enumFormat(material));

		} catch (Exception e) {

			return null;

		}

	}

}
