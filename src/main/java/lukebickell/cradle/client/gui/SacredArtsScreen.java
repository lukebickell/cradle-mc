package lukebickell.cradle.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lukebickell.cradle.client.ClientSacredArts;
import lukebickell.cradle.client.keymappings.CradleKeyBindings;
import lukebickell.cradle.common.ranks.SacredArtsRank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;

public class SacredArtsScreen extends Screen implements ClientSacredArts.Listener {
    private static final ResourceLocation WINDOW_LOCATION = new ResourceLocation("textures/gui/advancements/window.png");
    public static final int WINDOW_WIDTH = 252;
    public static final int WINDOW_HEIGHT = 140;

    private final ClientSacredArts sacredArts;

    public SacredArtsScreen(ClientSacredArts sacredArts) {
        super(NarratorChatListener.NO_TITLE);
        this.sacredArts = sacredArts;
    }

    protected void init() {
        this.sacredArts.setListener(this);
    }

    public void removed() {
        this.sacredArts.setListener(null);
    }

    public boolean mouseClicked(double x, double y, int p_97345_) {
        return super.mouseClicked(x, y, p_97345_);
    }

    public boolean keyPressed(int keySym, int scanCode, int p_97355_) {
        if (CradleKeyBindings.SACRED_ARTS.matches(keySym, scanCode)) {
            this.minecraft.setScreen(null);
            this.minecraft.mouseHandler.grabMouse();
            return true;
        } else {
            return super.keyPressed(keySym, scanCode, p_97355_);
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        int i = (this.width - 252) / 2;
        int j = (this.height - 140) / 2;
        this.renderBackground(poseStack);
        SacredArtsCoreWidget.renderCoreRepresentation(poseStack, mouseX, mouseY);
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    public void onUpdateSacredArts(int coreSize, SacredArtsRank rank) {
        
    }
}