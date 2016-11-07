
package mirror;

import org.bukkit.entity.Player;

public class PlayerWrapper {

    private ReflectedClass<Player> player;

    public PlayerWrapper(Player player) {

        this.player = new ReflectedClass<Player>(player);

    }

    public void sendPacket(Object packet) {

        getHandle().sendPacket(packet);

    }

    public void sendRaw(String message) {

        Object packet = new PacketBuilder("PlayOutChat").set("a", message.startsWith("{") ? Chat.fromJson(message) : Chat.componentText(message)).set("b", (byte) 1).create();

        sendPacket(packet);

    }

    public HandleWrapper getHandle() {

        return new HandleWrapper(player.getMethod("getHandle").invoke());

    }

}
