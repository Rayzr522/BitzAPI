
package mirror;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.rayzr522.bitzapi.utils.data.MapUtils;

public class JSONMessage {

	private Map<String, String> map = MapUtils.empty();

	public JSONMessage set(String key, String value) {

		map.put(key, value);

		return this;

	}

	public JSONMessage set(String key, JSONMessage message) {

		set(key, message.build());

		return this;

	}

	@SuppressWarnings("unchecked")
	public String build() {

		String output = "{";

		Entry<String, String>[] entries = (Entry<String, String>[]) map.entrySet().toArray();
		for (int i = 0; i < entries.length; i++) {

			if (i > 0) {
				output += ",";
			}

			Entry<String, String> entry = entries[i];
			output += "\"" + entry.getKey() + "\": \"" + entry.getValue() + "\"";

		}

		return output + "}";

	}

}
