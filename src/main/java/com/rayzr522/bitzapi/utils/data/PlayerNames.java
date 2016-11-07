
package com.rayzr522.bitzapi.utils.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 
 * This is a simple class which keeps tracks of associations between player
 * names and UUIDs
 * 
 * @author Rayzr
 *
 */
public class PlayerNames {

    private static HashMap<UUID, String> uuidToString = new HashMap<UUID, String>();
    private static HashMap<String, UUID> stringToUUID = new HashMap<String, UUID>();

    private static String                dataFilePath = "playerNames.db";

    private static Boolean               INITIALIZED  = false;

    /**
     * Do not EVER use this method. It will break things.
     * 
     * @param plugin
     */
    public static void init(JavaPlugin plugin) {

        if (INITIALIZED) {
            System.err.println("Plugin '" + plugin.getName() + "' attempted to initialize PlayerNames!");
            System.err.println("This should never be done, please report this to the plugin author.");
            return;
        }

        INITIALIZED = true;

        dataFilePath = plugin.getDataFolder() + File.separator + dataFilePath;

        plugin.getServer().getPluginManager().registerEvents(new PlayerNameListener(), plugin);

        new PlayerNamesSaveTask().runTaskTimer(plugin, 0, 6000);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(getDBFile());

        for (String key : config.getKeys(false)) {

            set(UUID.fromString(key), config.getString(key));

        }

    }

    private static File getDBFile() {

        if (!INITIALIZED) {
            return null;
        }

        File file = new File(dataFilePath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                System.err.println("Failed to create playerNames.db for the PlayerNames util.");
                System.err.println("Please report this to Rayzr on the Bukkit or Spigot forums along with the following stacktrace:");
                System.err.println("Attempted to create file at '" + file.getAbsolutePath() + "', dataFilePath = '" + dataFilePath + "'");
                e.printStackTrace();
            }
        }

        return file;

    }

    /**
     * Saves the stored UUID-name associations to the disk. This is
     * automatically run every 5 minutes. <br>
     * <br>
     * NOTE: This runs in a separate thread, so it shouldn't ever affect server
     * performance.
     */
    public static void save() {

        if (!INITIALIZED) {
            return;
        }

        new Thread(new Runnable() {

            @Override
            public void run() {

                YamlConfiguration config = new YamlConfiguration();

                for (Entry<UUID, String> pair : uuidToString.entrySet()) {

                    config.set(pair.getKey().toString(), pair.getValue());

                }

                File file = getDBFile();

                try {
                    config.save(file);
                } catch (Exception e) {
                    System.err.println("Failed to save to the playerNames.db file for the PlayerNames util.");
                    System.err.println("Please report this to Rayzr on the Bukkit or Spigot forums along with the following stacktrace:");
                    e.printStackTrace();
                }

            }

        }).start();

    }

    /**
     * Alias of {@code exists(p.getUniqueId());}
     * 
     * @param p
     *            the player
     * @return If there is a name stored for the given player
     */
    public static boolean exists(Player p) {

        if (!INITIALIZED) {
            return false;
        }

        return exists(p.getUniqueId());

    }

    /**
     * Whether or not there is a name stored for the given player
     * 
     * @param id
     *            the UUID of the player
     * @return If there is a name stored for the given UUID
     */
    public static boolean exists(UUID id) {

        if (!INITIALIZED) {
            return false;
        }

        return uuidToString.containsKey(id);

    }

    /**
     * Sets the given UUID-name association
     * 
     * @param id
     *            the UUID
     * @param name
     *            the name
     */
    public static void set(UUID id, String name) {

        if (!INITIALIZED) {
            return;
        }

        uuidToString.put(id, name);
        stringToUUID.put(name, id);

    }

    /**
     * Sets the value for the player. Alias of
     * {@code set(p.getUniqueId(), p.getName());}
     * 
     * @param p
     *            the player
     */
    public static void set(Player p) {

        if (!INITIALIZED) {
            return;
        }

        set(p.getUniqueId(), p.getName());

    }

    /**
     * Gets the UUID of a player with the given name, case-sensitive. If you
     * want to ignore case then use {@link PlayerNames#getIgnoreCase(String)}
     * 
     * @param name
     *            the name of the player, case sensitive
     * @return The UUID of the player with the given name, or null
     */
    public static UUID get(String name) {

        if (!INITIALIZED) {
            return null;
        }

        return stringToUUID.get(name);

    }

    /**
     * Get the last known name of the player with the given UUID
     * 
     * @param id
     *            the UUID of the player
     * @return The last known name, or null if the player has never been on the
     *         server
     */
    public static String get(UUID id) {

        if (!INITIALIZED) {
            return null;
        }

        return uuidToString.get(id);

    }

    /**
     * Gets the UUID of a player with the given name, ignoring case (might be
     * slower)
     * 
     * @param name
     *            the name of the player, ignoring case
     * @return The UUID of the player with the given name, or null
     */
    public static UUID getIgnoreCase(String name) {

        if (!INITIALIZED) {
            return null;
        }

        for (Entry<UUID, String> entry : uuidToString.entrySet()) {
            if (entry.getValue().toLowerCase().equals(name.toLowerCase())) {
                return entry.getKey();
            }
        }

        return null;

    }

    public static class PlayerNameListener implements Listener {

        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent e) {
            if (!PlayerNames.exists(e.getPlayer())) {
                PlayerNames.set(e.getPlayer());
            }
        }

    }

    public static class PlayerNamesSaveTask extends BukkitRunnable {

        @Override
        public void run() {
            PlayerNames.save();
        }

    }

}
