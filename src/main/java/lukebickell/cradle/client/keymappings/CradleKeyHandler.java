package lukebickell.cradle.client.keymappings;

import lukebickell.cradle.Cradle;
import lukebickell.cradle.client.gui.SacredArtsScreen;
import lukebickell.cradle.client.network.ClientCradleDataHandler;
import lukebickell.cradle.common.network.PacketHandler;
import lukebickell.cradle.common.network.CyclingPacketUpdate;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Cradle.MODID)
public class CradleKeyHandler {
    private static final Minecraft MINECRAFT = Minecraft.getInstance();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        // TODO, check if player is moving
        if (CradleKeyBindings.CYCLE_MADRA.isDown()) {
            PacketHandler.INSTANCE.sendToServer(new CyclingPacketUpdate(0.5F));
        }
    }

    @SubscribeEvent
    public static void onKeyPressed(InputEvent.KeyInputEvent event) {
        if(MINECRAFT.player == null || MINECRAFT.screen != null || event.getAction() != 1) {
            return;
        }

        if (event.getKey() == CradleKeyBindings.SACRED_ARTS.getKey().getValue()) {
            if (MINECRAFT.screen == null) {
                //MINECRAFT.setScreen(new SacredArtsScreen(ClientCradleDataHandler.getSacredArts())); disable until it's ACTUALLY WORKING GOD DANG
            }
        }
    }

}
