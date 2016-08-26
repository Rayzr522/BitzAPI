
package mirror;

import com.rayzr522.bitzapi.utils.Reflection;

public class PacketBuilder {

	private ReflectedClass<Object> packet;

	public PacketBuilder(String packet) {

		Class<?> packetClass = getPacketClass(packet);
		if (packetClass == null) {
			System.err.println("Invalid packet class: '" + packet + "'");
			return;
		}

		Object obj;
		try {
			obj = packetClass.newInstance();
		} catch (Exception e) {
			System.err.println("Failed to instantiate Packet class: '" + packetClass.getCanonicalName() + "'");
			e.printStackTrace();
			return;
		}

		this.packet = new ReflectedClass<Object>(obj);

	}

	public PacketBuilder set(String field, Object value) {

		packet.setField(field, value);

		return this;

	}

	public Object create() {

		return packet.getObject();

	}

	private Class<?> getPacketClass(String name) {

		return Reflection.getNMS("Packet" + name);

	}

}
