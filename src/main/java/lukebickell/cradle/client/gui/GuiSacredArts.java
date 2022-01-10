package lukebickell.cradle.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lukebickell.cradle.Cradle;
import lukebickell.cradle.client.ClientSacredArts;
import lukebickell.cradle.client.network.ClientCradleDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cradle.MODID)
public class GuiSacredArts {
    private static final Minecraft minecraft = Minecraft.getInstance();

    @SubscribeEvent
    public static void drawOverlay(RenderGameOverlayEvent.Post event) {
        ClientSacredArts sacredArts = ClientCradleDataHandler.getSacredArts();
        SacredArtsCoreWidget coreWidget = new SacredArtsCoreWidget(sacredArts.getRank().block);
        PoseStack poseStack = RenderSystem.getModelViewStack();
        poseStack.pushPose();
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        coreWidget.draw((screenWidth / 2), screenHeight - 22 - 22, sacredArts.getPercentMadraFull(), SacredArtsCoreWidget.Speed.FAST);
        poseStack.popPose();
    }

    @SubscribeEvent
    public static void drawText(RenderGameOverlayEvent.Text event) {
        ClientSacredArts sacredArts = ClientCradleDataHandler.getSacredArts();
        if (sacredArts.canRankUp()) {
            minecraft.font.draw(new PoseStack(), "Press 'V' to rank up!", 0, 0, 5);
        }
    }
}