
package com.rayzr522.bitzapi.config.handlers;

import java.util.Map;

import org.bukkit.util.Vector;

import com.rayzr522.bitzapi.config.data.ISerializationHandler;
import com.rayzr522.bitzapi.utils.data.MapUtils;

public class VectorSerializer implements ISerializationHandler<Vector> {

	@Override
	public Map<String, Object> serialize(Vector obj) {

		Map<String, Object> map = MapUtils.empty();

		map.put("x", obj.getX());
		map.put("y", obj.getY());
		map.put("z", obj.getZ());

		return map;
	}

	@Override
	public Vector deserialize(Map<String, Object> map) {

		double x = (double) map.get("x");
		double y = (double) map.get("y");
		double z = (double) map.get("z");

		return new Vector(x, y, z);
	}

}
