
package com.rayzr522.bitzapi.config.handlers;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.rayzr522.bitzapi.config.data.ISerializationHandler;
import com.rayzr522.bitzapi.utils.data.MapUtils;

public class WorldSerializer implements ISerializationHandler<World> {

    @Override
    public Map<String, Object> serialize(World obj) {

        Map<String, Object> map = MapUtils.empty();

        map.put("uuid", obj.getUID().toString());

        return map;
    }

    @Override
    public World deserialize(Map<String, Object> map) {

        UUID uuid = UUID.fromString((String) map.get("uuid"));

        World world = Bukkit.getWorld(uuid);

        return world;

    }

}
