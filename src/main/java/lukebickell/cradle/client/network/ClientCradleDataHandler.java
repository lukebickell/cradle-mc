package lukebickell.cradle.client.network;


import lukebickell.cradle.client.ClientSacredArts;
import lukebickell.cradle.common.network.packet.ClientBoundUpdateSacredArtsPacket;

public class ClientCradleDataHandler {
    private final static ClientSacredArts SACRED_ARTS = new ClientSacredArts();

    public static void handleUpdateSacredArtsPacket(ClientBoundUpdateSacredArtsPacket packet) {
        SACRED_ARTS.update(packet);
    }

    public static ClientSacredArts getSacredArts() {
        return SACRED_ARTS;
    }
}
