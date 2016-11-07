
package mirror;

import static mirror.Mirror.$;

import com.rayzr522.bitzapi.utils.Reflection;

public class HandleWrapper {

    private static Class<?>        packetClass = Reflection.getNMS("Packet");

    private ReflectedClass<Object> handle;
    private Object                 connection;
    private ReflectedMethod        sendPacketMethod;

    public HandleWrapper(Object handle) {

        this.handle = new ReflectedClass<Object>(handle);

        connection = this.handle.getField("playerConnection");
        sendPacketMethod = $(connection).getMethod("sendPacket", packetClass);

    }

    public void sendPacket(Object packet) {

        sendPacketMethod.invoke(packet);

    }

}
