package lukebickell.cradle.common.network;

import lukebickell.cradle.common.capability.SacredArtsCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ServerBoundUpdateCyclingPacket {
    private static final Logger LOGGER = LogManager.getLogger();

    public float cyclingGameScore;

    public ServerBoundUpdateCyclingPacket(float cyclingGameScore) {
        this.cyclingGameScore = cyclingGameScore;
    }

    // Decode
    public ServerBoundUpdateCyclingPacket(FriendlyByteBuf buffer) {
        this(buffer.readFloat());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeFloat(cyclingGameScore);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            ctx.get().getSender().getCapability(SacredArtsCapability.INSTANCE, null).ifPresent(spirit -> {
                spirit.addOrSubtractToCore(Math.round(cyclingGameScore * 100));
                LOGGER.info(spirit.getCoreSize() + ": Successfully added " + Math.round(cyclingGameScore * 100) + " spirit to player " + ctx.get().getSender().getName().getContents());
                success.set(true);
            });
        });
        ctx.get().setPacketHandled(true);
        return success.get();
    }

}
