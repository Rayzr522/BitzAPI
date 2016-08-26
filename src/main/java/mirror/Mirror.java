/**
 * mirror was made by Jadon Fowler.
 * This file is licensed under the MIT License.
 */

package mirror;

import org.bukkit.entity.Player;

/**
 * @author Jadon "Phase" Fowler on Feb 16, 2015
 */
public class Mirror {

	public static <T> ReflectedClass<T> $(T o) {
		return new ReflectedClass<T>(o);
	}

	public static PlayerWrapper $(Player p) {
		return new PlayerWrapper(p);
	}

}
