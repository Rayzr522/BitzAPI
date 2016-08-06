
package com.rayzr522.bitzapi.utils.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.rayzr522.bitzapi.config.Serializable;

public class PlayerData implements Serializable<PlayerData> {

	private List<String>				keys	= Arrays.asList("player", "canFly", "isFlying", "health", "exp", "level", "food", "inv", "armor", "gameMode", "potionEffects");

	private Player						player;

	private boolean						canFly;
	private boolean						isFlying;

	private double						health;
	private float						exp;
	private int							level;
	private int							food;

	private ItemStack[]					inv;
	private ItemStack[]					armor;

	private GameMode					gameMode;

	private Collection<PotionEffect>	potionEffects;

	private boolean						stored	= false;

	public PlayerData(Player player, boolean store, boolean clear) {

		this.player = player;

		if (store) {
			store(clear);
		}

	}

	public PlayerData(Player player) {

		this(player, false, false);

	}

	public PlayerData() {

		this(null, false, false);

	}

	/**
	 * Clears the player's inventory, sets their food and health to 20 and
	 * clears their potion effects
	 */
	public void clearPlayer() {

		player.getInventory().clear();
		player.setHealth(20);
		player.setFoodLevel(20);
		player.getActivePotionEffects().clear();

	}

	/**
	 * Stores
	 * 
	 * @param clear
	 *            whether or not to clear the player after storing
	 *            {@link PlayerData#clearPlayer()}
	 * @return
	 */
	public PlayerData store(boolean clear) {

		canFly = player.getAllowFlight();
		isFlying = player.isFlying();

		health = player.getHealth();
		exp = player.getExp();
		level = player.getLevel();
		food = player.getFoodLevel();

		inv = player.getInventory().getContents();
		armor = player.getInventory().getArmorContents();

		gameMode = player.getGameMode();
		potionEffects = player.getActivePotionEffects();

		stored = true;

		if (clear) {

			clearPlayer();

		}

		return this;

	}

	/**
	 * Sets the player's stats to what they were before storing. If the player's
	 * stats haven't been stored yet then it won't do anything.
	 * 
	 * @return the PlayerData object
	 */
	public PlayerData restore() {

		if (!stored) { return null; }

		player.setAllowFlight(canFly);
		player.setFlying(isFlying);

		player.setHealth(health);
		player.setExp(exp);
		player.setLevel(level);
		player.setFoodLevel(food);

		player.getInventory().setContents(inv);
		player.getInventory().setArmorContents(armor);

		player.setGameMode(gameMode);

		player.getActivePotionEffects().clear();
		player.getActivePotionEffects().addAll(potionEffects);

		return this;

	}

	/**
	 * @return whether or not the player's data has been stored yet
	 */
	public boolean isStored() {
		return stored;
	}

	/**
	 * Serializes this PlayerData instance into a {@code <String, Object>} map
	 * for storing purposes
	 */
	public Map<String, Object> serialize() {

		Map<String, Object> serialized = MapUtils.empty();

		serialized.put("player", player.getUniqueId().toString());
		serialized.put("canFly", canFly);
		serialized.put("isFlying", isFlying);
		serialized.put("health", health);
		serialized.put("exp", exp);
		serialized.put("level", level);
		serialized.put("food", food);
		serialized.put("inv", inv != null ? Arrays.asList(inv) : ListUtils.<ItemStack> empty());
		serialized.put("armor", armor != null ? Arrays.asList(armor) : ListUtils.<ItemStack> empty());
		serialized.put("gameMode", gameMode.name());

		List<PotionEffect> effects = ListUtils.empty();

		if (potionEffects != null) {
			effects.addAll(potionEffects);
		}

		serialized.put("potionEffects", effects);

		return serialized;

	}

	/**
	 * Constructs a PlayerData object from a {@code <String, Object>} map
	 */
	@SuppressWarnings("unchecked")
	public PlayerData deserialize(Map<String, Object> serialized) {

		if (!serialized.containsKey("player")) { return null; }

		for (String key : keys) {
			if (!serialized.containsKey(key)) {
				serialized.put(key, null);
			}
		}

		UUID id = UUID.fromString((String) serialized.get("player"));
		Player player = Bukkit.getPlayer(id);

		if (player == null) { return null; }

		this.player = player;

		canFly = Deserializer.bool((String) serialized.get("canFly"));
		isFlying = Deserializer.bool((String) serialized.get("isFlying"));
		health = Double.parseDouble((String) serialized.get("health"));
		exp = Float.parseFloat((String) serialized.get("exp"));
		level = Integer.parseInt((String) serialized.get("level"));
		food = Integer.parseInt((String) serialized.get("food"));
		inv = (ItemStack[]) serialized.get("inv");
		armor = (ItemStack[]) serialized.get("armor");
		gameMode = GameMode.valueOf((String) serialized.get("gameMode"));
		potionEffects = (List<PotionEffect>) serialized.get("potionEffects");

		return this;

	}

	/**
	 * @return the keys
	 */
	public List<String> getKeys() {
		return keys;
	}

	/**
	 * @param keys
	 *            the keys to set
	 */
	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the canFly
	 */
	public boolean isCanFly() {
		return canFly;
	}

	/**
	 * @param canFly
	 *            the canFly to set
	 */
	public void setCanFly(boolean canFly) {
		this.canFly = canFly;
	}

	/**
	 * @return the isFlying
	 */
	public boolean isFlying() {
		return isFlying;
	}

	/**
	 * @param isFlying
	 *            the isFlying to set
	 */
	public void setFlying(boolean isFlying) {
		this.isFlying = isFlying;
	}

	/**
	 * @return the health
	 */
	public double getHealth() {
		return health;
	}

	/**
	 * @param health
	 *            the health to set
	 */
	public void setHealth(double health) {
		this.health = health;
	}

	/**
	 * @return the exp
	 */
	public float getExp() {
		return exp;
	}

	/**
	 * @param exp
	 *            the exp to set
	 */
	public void setExp(float exp) {
		this.exp = exp;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the food
	 */
	public int getFood() {
		return food;
	}

	/**
	 * @param food
	 *            the food to set
	 */
	public void setFood(int food) {
		this.food = food;
	}

	/**
	 * @return the inv
	 */
	public ItemStack[] getInv() {
		return inv;
	}

	/**
	 * @param inv
	 *            the inv to set
	 */
	public void setInv(ItemStack[] inv) {
		this.inv = inv;
	}

	/**
	 * @return the armor
	 */
	public ItemStack[] getArmor() {
		return armor;
	}

	/**
	 * @param armor
	 *            the armor to set
	 */
	public void setArmor(ItemStack[] armor) {
		this.armor = armor;
	}

	/**
	 * @return the gameMode
	 */
	public GameMode getGameMode() {
		return gameMode;
	}

	/**
	 * @param gameMode
	 *            the gameMode to set
	 */
	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	/**
	 * @return the potionEffects
	 */
	public Collection<PotionEffect> getPotionEffects() {
		return potionEffects;
	}

	/**
	 * @param potionEffects
	 *            the potionEffects to set
	 */
	public void setPotionEffects(Collection<PotionEffect> potionEffects) {
		this.potionEffects = potionEffects;
	}

}
