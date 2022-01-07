package lukebickell.cradle.common.network;

import lukebickell.cradle.Cradle;
import lukebickell.cradle.common.network.packet.ClientBoundUpdateSacredArtsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    public static SimpleChannel INSTANCE;

    private static final String PROTOCOL_VERSION = "1.0";
    private static int ID = 0;
    public static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        System.out.println("Cradle: Registering Packets!!");
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Cradle.MODID, "network"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals);

        INSTANCE.messageBuilder(ServerBoundUpdateCyclingPacket.class, nextID(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(ServerBoundUpdateCyclingPacket::encode)
                .decoder(ServerBoundUpdateCyclingPacket::new)
                .consumer(ServerBoundUpdateCyclingPacket::handle)
                .add();

        INSTANCE.messageBuilder(ClientBoundUpdateSacredArtsPacket.class, nextID(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientBoundUpdateSacredArtsPacket::encode)
                .decoder(ClientBoundUpdateSacredArtsPacket::new)
                .consumer(ClientBoundUpdateSacredArtsPacket::handle)
                .add();
    }
}
