package lukebickell.cradle.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SacredArtsCoreWidget extends GuiComponent {

    private final int MAX_SIZE_SCALE = 24;
    private final int MIN_SIZE_SCALE = 12;

    private Block coreBlock;

    public SacredArtsCoreWidget(Block block) {
        this.coreBlock = block;
    }

    public void draw(int x, int y, float percentMadraFull, Speed rotationSpeed) {
        int xBlockCenter = x;
        int yBlockCenter = y;

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack poseStack = RenderSystem.getModelViewStack();
        poseStack.pushPose();
        poseStack.translate(xBlockCenter, yBlockCenter, -50);
        poseStack.scale(1.0F, -1.0F, 1.0F);
        RenderSystem.applyModelViewMatrix();
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();

        PoseStack blockPoseStack = new PoseStack();
        blockPoseStack.pushPose();
        applyTimedRotation(blockPoseStack, rotationSpeed);
        float coreScale = calculatePercentageScale(percentMadraFull);
        blockPoseStack.scale(coreScale, coreScale, coreScale);

        this.renderBlock(new ItemStack(this.coreBlock), blockPoseStack, bufferSource);

        PoseStack glassPoseStack = new PoseStack();
        glassPoseStack.pushPose();
        applyTimedRotation(glassPoseStack, rotationSpeed);
        glassPoseStack.scale(MAX_SIZE_SCALE, MAX_SIZE_SCALE, MAX_SIZE_SCALE);
        this.renderBlock(new ItemStack(Blocks.GLASS), glassPoseStack, bufferSource);

        bufferSource.endBatch();
        RenderSystem.enableDepthTest();
        Lighting.setupFor3DItems();

        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    // Returns value between MIN_SIZE_SCALE and MAX_SIZE_SCALE based on currentMadra/maxMadra
    private float calculatePercentageScale(float percentMaxMadra) {
        if (percentMaxMadra <= 0) {
            return MIN_SIZE_SCALE;
        } else if (percentMaxMadra >= 1) {
            return MAX_SIZE_SCALE + 0.5f;
        }
        int minMaxDiff = MAX_SIZE_SCALE - MIN_SIZE_SCALE;
        return MIN_SIZE_SCALE + (minMaxDiff * percentMaxMadra);
    }

    private void renderBlock(ItemStack block, PoseStack poseStack, MultiBufferSource.BufferSource bufferSource) {
        Minecraft.getInstance().getItemRenderer().renderStatic(
                block,
                ItemTransforms.TransformType.FIXED,
                15728880,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                bufferSource,
                0);
    }

    private void applyTimedRotation(PoseStack poseStack, Speed rotationSpeed) {
        float angle = (System.currentTimeMillis() / rotationSpeed.value) % 360;
        Quaternion rotationX = Vector3f.XP.rotationDegrees(angle);
        Quaternion rotationY = Vector3f.YP.rotationDegrees(angle);

        poseStack.mulPose(rotationX);
        poseStack.mulPose(rotationY);
    }

    public enum Speed {
        SLOW(50),
        FAST(15),
        FASTER(5);

        public final int value;

        private Speed(int value) {
            this.value = value;
        }
    }
}
