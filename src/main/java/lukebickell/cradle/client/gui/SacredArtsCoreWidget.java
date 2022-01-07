package lukebickell.cradle.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL32;

public class SacredArtsCoreWidget extends GuiComponent {

    public static void render3DCore(Block coreBlock) {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack poseStack = RenderSystem.getModelViewStack();
        poseStack.pushPose();
        poseStack.translate(175.0F, 125.0F, 50.0F);
        poseStack.translate(8.0D, 8.0D, 0.0D);
        poseStack.scale(1.0F, -1.0F, 1.0F);
        poseStack.scale(16.0F, 16.0F, 16.0F);
        RenderSystem.applyModelViewMatrix();

        PoseStack blockPoseStack = new PoseStack();
        blockPoseStack.pushPose();
        applyTimedRotation(blockPoseStack);
        blockPoseStack.scale(4, 4, 4);
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();

        ItemStack item = new ItemStack(coreBlock);
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.FIXED, 15728880, OverlayTexture.NO_OVERLAY, blockPoseStack, bufferSource, 0);
        bufferSource.endBatch();
        RenderSystem.enableDepthTest();
        //Lighting.setupFor3DItems();

        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    private static void applyTimedRotation(PoseStack poseStack) {
        float angle = (System.currentTimeMillis() / 15) % 360;
        Quaternion rotationX = Vector3f.XP.rotationDegrees(angle);
        Quaternion rotationY = Vector3f.YP.rotationDegrees(angle);
        //Quaternion rotationZ = Vector3f.ZP.rotationDegrees(angle);

        poseStack.mulPose(rotationX);
        poseStack.mulPose(rotationY);
    }
}
