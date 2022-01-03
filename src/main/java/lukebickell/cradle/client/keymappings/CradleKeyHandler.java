package lukebickell.cradle.client.keymappings;

import lukebickell.cradle.Cradle;
import lukebickell.cradle.common.network.PacketHandler;
import lukebickell.cradle.common.network.CyclingPacketUpdate;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Cradle.MODID)
public class CradleKeyHandler {
    private static final Minecraft MINECRAFT = Minecraft.getInstance();

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        // TODO, check if player is moving
        if (CradleKeyBindings.CYCLE_MADRA.isDown()) {
            PacketHandler.INSTANCE.sendToServer(new CyclingPacketUpdate(0.5F));
        }
    }

}
