package lukebickell.cradle.common.network;

import lukebickell.cradle.client.ClientData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ClientPlayerSacredArtsPacket {

    public final CompoundTag sacredArts;

    public ClientPlayerSacredArtsPacket(CompoundTag sacredArts) {
        this.sacredArts = sacredArts;
    }

    public ClientPlayerSacredArtsPacket(FriendlyByteBuf buffer) {
        this(buffer.readAnySizeNbt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeNbt(this.sacredArts);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                ClientData.sacredArts = this.sacredArts;
                success.set(true);
            });
        });
        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
