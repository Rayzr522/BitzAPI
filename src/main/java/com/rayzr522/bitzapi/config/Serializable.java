
package com.rayzr522.bitzapi.config;

import java.util.Map;

public interface Serializable<T> {

	public Map<String, Object> serialize();

	public T deserialize(Map<String, Object> serialized);

}
