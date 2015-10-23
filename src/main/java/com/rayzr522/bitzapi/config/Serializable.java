package com.rayzr522.bitzapi.config;

import java.util.Map;

public interface Serializable {
	
	public Map<String, Object> serialize();

	public Object deserialize(Map<String, Object> serialized);

}
