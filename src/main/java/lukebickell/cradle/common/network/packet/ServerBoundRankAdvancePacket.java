package lukebickell.cradle.common.network.packet;

import lukebickell.cradle.common.capability.SacredArtsCapability;
import lukebickell.cradle.common.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ServerBoundRankAdvancePacket {

    public ServerBoundRankAdvancePacket() {}
    public ServerBoundRankAdvancePacket(FriendlyByteBuf buf) {}
    public void encode(FriendlyByteBuf buf) {}

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            Player sender = ctx.get().getSender();
            sender.getCapability(SacredArtsCapability.INSTANCE, null).ifPresent(sacredArts -> {
                sacredArts.advanceToNextRank();
                PacketHandler.updateClientSacredArts((ServerPlayer) sender, sacredArts);
                success.set(true);
            });
        });
        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
