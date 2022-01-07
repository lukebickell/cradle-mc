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

public class SacredArtsCoreWidget extends GuiComponent {

    private Block coreBlock;

    public SacredArtsCoreWidget(Block block) {
        this.coreBlock = block;
    }

    public void draw(int x, int y, int scale, Speed rotationSpeed) {
        int xBlockCenter = x;
        int yBlockCenter = y;

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack poseStack = RenderSystem.getModelViewStack();
        poseStack.pushPose();
        poseStack.translate(xBlockCenter, yBlockCenter, 50.0F);
        poseStack.scale(1.0F, -1.0F, 1.0F);
        RenderSystem.applyModelViewMatrix();

        PoseStack blockPoseStack = new PoseStack();
        blockPoseStack.pushPose();
        applyTimedRotation(blockPoseStack, rotationSpeed);
        blockPoseStack.scale(scale, scale, scale);
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();

        Minecraft.getInstance().getItemRenderer().renderStatic(
                new ItemStack(this.coreBlock),
                ItemTransforms.TransformType.FIXED,
                15728880,
                OverlayTexture.NO_OVERLAY,
                blockPoseStack,
                bufferSource,
                0);

        bufferSource.endBatch();
        RenderSystem.enableDepthTest();
        Lighting.setupFor3DItems();

        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    private static void applyTimedRotation(PoseStack poseStack, Speed rotationSpeed) {
        float angle = (System.currentTimeMillis() / rotationSpeed.value) % 360;
        Quaternion rotationX = Vector3f.XP.rotationDegrees(angle);
        Quaternion rotationY = Vector3f.YP.rotationDegrees(angle);

        poseStack.mulPose(rotationX);
        poseStack.mulPose(rotationY);
    }

    public static enum Speed {
        SLOW(50),
        FAST(15),
        FASTER(5);

        public final int value;

        private Speed(int value) {
            this.value = value;
        }
    }
}
