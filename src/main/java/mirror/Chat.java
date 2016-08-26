
package mirror;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.rayzr522.bitzapi.utils.Reflection;

public class Chat {

	private static Constructor<?>	chatComponentText;
	private static Method			stringToChat;

	static {

		try {

			chatComponentText = Reflection.getNMS("ChatComponentText").getConstructor(String.class);

			if (Reflection.isVersion("10") || Reflection.isVersion("9") || Reflection.isVersion("8")) {
				stringToChat = Reflection.getNMS("IChatBaseComponent$ChatSerializer").getMethod("a", String.class);
			} else {
				stringToChat = Reflection.getNMS("ChatSerializer").getMethod("a", String.class);;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Object componentText(String msg) {

		try {
			return chatComponentText.newInstance(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Object fromJson(String json) {

		json = json.replace("''", "\\\"");
		json = json.replace("'", "\"");

		System.out.println(json);

		try {
			return stringToChat.invoke(null, json);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
