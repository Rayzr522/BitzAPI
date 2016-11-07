
package com.rayzr522.bitzapi.config;

import java.util.Map;

@Deprecated
public interface DEPRECATED_Serializable<T> {

    public Map<String, Object> serialize();

    public T deserialize(Map<String, Object> serialized);

}
