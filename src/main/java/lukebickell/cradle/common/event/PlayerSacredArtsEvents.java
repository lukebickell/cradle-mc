package lukebickell.cradle.common.event;

import lukebickell.cradle.Cradle;
import lukebickell.cradle.common.capability.SacredArtsCapability;
import lukebickell.cradle.common.network.PacketHandler;
import lukebickell.cradle.common.network.packet.ClientBoundUpdateSacredArtsPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = Cradle.MODID)
public class PlayerSacredArtsEvents {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // TODO passive madra replenishing: https://github.com/baileyholl/Ars-Nouveau/blob/1.18.x/src/main/java/com/hollingsworth/arsnouveau/common/event/ManaCapEvents.java
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        syncPlayerSacredArts(event.getPlayer());
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.getOriginal().level.isClientSide) {
            return;
        }
        syncPlayerSacredArts(event.getOriginal());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.StartTracking event) {
        syncPlayerSacredArts(event.getPlayer());
    }

    public static void syncPlayerSacredArts(Player player) {
        if (player instanceof ServerPlayer) {
            player.getCapability(SacredArtsCapability.INSTANCE, null).ifPresent(sacredArts -> {
                PacketHandler.updateClientSacredArts((ServerPlayer) player, sacredArts);
            });
        }
    }
}
