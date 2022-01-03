package lukebickell.cradle.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import lukebickell.cradle.Cradle;
import lukebickell.cradle.client.ClientData;
import lukebickell.cradle.common.capability.ISacredArts;
import lukebickell.cradle.common.capability.SacredArtsCapability;
import lukebickell.cradle.common.capability.SacredArtsImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cradle.MODID)
public class GuiSacredArts {
    private static final Minecraft minecraft = Minecraft.getInstance();

    @SubscribeEvent
    public static void writeText(RenderGameOverlayEvent.Text event) {
        int coreSize = ClientData.sacredArts.getInt("cradleSpiritCoreSize");

        minecraft.font.draw(new PoseStack(), "Spirit: " + coreSize, 0, 0, 5);
    }
}
