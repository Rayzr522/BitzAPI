
package com.rayzr522.bitzapi.utils.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.rayzr522.bitzapi.utils.item.ItemUtils;

public class LoreData {

	public static final String IDENTIFIER = ChatColor.BLACK.toString() + ChatColor.MAGIC.toString() + "__LOREDATA:";

	public static boolean isData(String text) {

		return (text.startsWith(IDENTIFIER) && text.substring(IDENTIFIER.length()).contains("::"));

	}

	public static void clearData(ItemStack item) {

		if (ItemUtils.isEmpty(item)) { return; }

		List<String> lore = ItemUtils.getLore(item);
		if (lore == null) { return; }

		for (String str : lore) {

			if (!isData(str)) {

				continue;

			}

			lore.remove(str);

		}

		ItemUtils.setLore(item, lore);

	}

	private HashMap<String, Object> data = MapUtils.<String, Object> empty();

	public LoreData(ItemStack item) {

		if (ItemUtils.isEmpty(item)) { return; }

		List<String> lore = ItemUtils.getLore(item);
		if (lore == null) { return; }

		for (String str : lore) {

			if (!isData(str)) {
				continue;
			}

			String[] split = str.substring(IDENTIFIER.length()).split("::");

			data.put(split[0], ArrayUtils.concatArray(ArrayUtils.removeFirst(split), "::"));

		}

	}

	public void write(ItemStack item) {

		if (ItemUtils.isEmpty(item)) { return; }

		clearData(item);

		append(item);

	}

	@SuppressWarnings("unchecked")
	public void append(ItemStack item) {

		if (ItemUtils.isEmpty(item)) { return; }

		List<String> dataLore = ListUtils.empty();

		for (Entry<String, Object> entry : data.entrySet()) {
			dataLore.add(IDENTIFIER + entry.getKey() + "::" + entry.getValue().toString());
		}

		List<String> lore = ItemUtils.getLore(item);
		if (lore == null) {
			lore = ListUtils.<String> empty();
		}

		ItemUtils.setLore(item, ListUtils.<String> combine(dataLore, lore));

	}

	public Object get(String key) {

		return data.get(key);

	}

	public String getString(String key) {

		return get(key) == null ? null : get(key).toString();

	}

	public boolean getBool(String key) {

		return Boolean.parseBoolean(getString(key));

	}

	public int getInt(String key) {

		try {

			return Integer.parseInt(getString(key));

		} catch (Exception e) {

			return -1;

		}

	}

	public float getFloat(String key) {

		try {

			return Float.parseFloat(getString(key));

		} catch (Exception e) {

			return -1;

		}

	}

	public double getDouble(String key) {

		try {

			return Double.parseDouble(getString(key));

		} catch (Exception e) {

			return -1;

		}

	}

	public void set(String key, Object value) {

		data.put(key, value);

	}

}
