package lukebickell.cradle.common.network.packet;

import lukebickell.cradle.client.network.ClientCradleDataHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ClientBoundUpdateSacredArtsPacket {

    public final CompoundTag sacredArts;

    public ClientBoundUpdateSacredArtsPacket(CompoundTag sacredArts) {
        this.sacredArts = sacredArts;
    }

    public ClientBoundUpdateSacredArtsPacket(FriendlyByteBuf buffer) {
        this(buffer.readNbt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeNbt(this.sacredArts);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                ClientCradleDataHandler.handleUpdateSacredArtsPacket(this);
            });
        });
        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
