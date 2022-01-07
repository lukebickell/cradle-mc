package lukebickell.cradle.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lukebickell.cradle.client.ClientSacredArts;
import lukebickell.cradle.client.keymappings.CradleKeyBindings;
import lukebickell.cradle.common.ranks.SacredArtsRank;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class SacredArtsScreen extends Screen implements ClientSacredArts.Listener {
    private static final ResourceLocation WINDOW_LOCATION = new ResourceLocation("textures/gui/advancements/window.png");
    public static final int WINDOW_WIDTH = 252;
    public static final int WINDOW_HEIGHT = 140;
    private static final Component TITLE = new TranslatableComponent("gui.cradle.spirit");

    private final SacredArtsOverview sacredArtsOverview;

    private final ClientSacredArts sacredArts;

    public SacredArtsScreen(ClientSacredArts sacredArts) {
        super(NarratorChatListener.NO_TITLE);
        this.sacredArts = sacredArts;
        this.sacredArtsOverview = new SacredArtsOverview(sacredArts);
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
        int i = (this.width - WINDOW_WIDTH) / 2;
        int j = (this.height - WINDOW_HEIGHT) / 2;
        this.renderBackground(poseStack);
        //super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderInside(poseStack, i, j);
        this.renderWindow(poseStack, i, j);
    }

    public void renderInside(PoseStack poseStack, int windowOriginX, int windowOriginY) {
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate(windowOriginX + 9, windowOriginY + 18, 0.0D);
        RenderSystem.applyModelViewMatrix();
        this.sacredArtsOverview.drawContents(poseStack, WINDOW_WIDTH - 18, WINDOW_HEIGHT - 27);
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.depthFunc(515);
        RenderSystem.disableDepthTest();
    }

    public void renderWindow(PoseStack poseStack, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WINDOW_LOCATION);
        this.blit(poseStack, x, y, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        this.font.draw(poseStack, TITLE, (float)(x + 8), (float)(y + 6), 4210752);
    }

    public void onUpdateSacredArts(int coreSize, SacredArtsRank rank) {
        
    }
}
