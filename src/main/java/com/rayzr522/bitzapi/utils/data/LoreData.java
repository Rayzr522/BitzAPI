
package com.rayzr522.bitzapi.utils.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.rayzr522.bitzapi.utils.item.ItemUtils;

public class LoreData {

	/**
	 * The small line of text that comes before all LoreData lines
	 */
	public static final String		IDENTIFIER	= ChatColor.BLACK.toString() + ChatColor.MAGIC.toString() + "__LD:";

	private HashMap<String, Object>	data		= MapUtils.<String, Object> empty();

	/**
	 * Returns whether or not {@code text} is a LoreData line
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isData(String text) {

		return (text.startsWith(IDENTIFIER) && text.substring(IDENTIFIER.length()).contains("::"));

	}

	/**
	 * Removes all LoreData lines
	 * 
	 * @param item
	 */
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

	/**
	 * Write the data to {@code item}
	 * 
	 * @param item
	 */
	public void write(ItemStack item) {

		if (ItemUtils.isEmpty(item)) { return; }

		clearData(item);

		append(item);

	}

	/**
	 * Append the data to {@code item}
	 * 
	 * @param item
	 */
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

		dataLore.addAll(lore);

		ItemUtils.setLore(item, dataLore);

	}

	/**
	 * Gets the data stored at {@code key}, or null if it doesn't exist
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {

		return data.get(key);

	}

	/**
	 * Gets a string stored at {@code key}, or null if it doesn't exist
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {

		return get(key) == null ? null : get(key).toString();

	}

	/**
	 * Gets a boolean stored at {@code key}, or false if it doesn't exist
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBool(String key) {

		return getString(key) == null ? false : Boolean.parseBoolean(getString(key));

	}

	/**
	 * Gets an int stored at {@code key}, or -1 if it doesn't exist
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {

		try {

			return Integer.parseInt(getString(key));

		} catch (Exception e) {

			return -1;

		}

	}

	/**
	 * Gets a float stored at {@code key}, or -1f if it doesn't exist
	 * 
	 * @param key
	 * @return
	 */
	public float getFloat(String key) {

		try {

			return Float.parseFloat(getString(key));

		} catch (Exception e) {

			return -1f;

		}

	}

	/**
	 * Gets a double stored at {@code key}, or -1.0 if it doesn't exist
	 * 
	 * @param key
	 * @return
	 */
	public double getDouble(String key) {

		try {

			return Double.parseDouble(getString(key));

		} catch (Exception e) {

			return -1.0;

		}

	}

	/**
	 * Sets the {@code value} at {@code key}
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {

		data.put(key, value);

	}

}
