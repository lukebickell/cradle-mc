package lukebickell.cradle.common.network;

import lukebickell.cradle.Cradle;
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

        INSTANCE.messageBuilder(CyclingPacketUpdate.class, nextID(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(CyclingPacketUpdate::encode)
                .decoder(CyclingPacketUpdate::new)
                .consumer(CyclingPacketUpdate::handle)
                .add();

        INSTANCE.messageBuilder(ClientPlayerSacredArtsPacket.class, nextID(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientPlayerSacredArtsPacket::encode)
                .decoder(ClientPlayerSacredArtsPacket::new)
                .consumer(ClientPlayerSacredArtsPacket::handle)
                .add();
    }
}
