package lukebickell.cradle.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import lukebickell.cradle.Cradle;
import lukebickell.cradle.client.ClientSacredArts;
import lukebickell.cradle.client.network.ClientCradleDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cradle.MODID)
public class GuiSacredArts extends Screen {
    private static final Minecraft minecraft = Minecraft.getInstance();

    GuiSacredArts() {
        super(new TranslatableComponent("cradle.gui.sacredarts"));
    }

    @SubscribeEvent
    public static void writeText(RenderGameOverlayEvent.Text event) {
        ClientSacredArts sacredArts = ClientCradleDataHandler.getSacredArts();
        int coreSize = sacredArts.getCoreSize();
        String rankText = sacredArts.getRankName();

        minecraft.font.draw(new PoseStack(), "Spirit: " + coreSize, 0, 0, 5);
        minecraft.font.draw(new PoseStack(), "Rank: " +  rankText, 0, 10, 5);
    }
}
