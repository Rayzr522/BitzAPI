
package com.rayzr522.bitzapi.config.data;

public interface Serializable {

	/**
	 * Called when the variable is loaded from serialized data. Good for setting
	 * values based on loaded data.
	 */
	public void onDeserialize();

}
