package lukebickell.cradle.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

public class SacredArtsCoreWidget extends GuiComponent {

    public static void renderCoreRepresentation(PoseStack poseStack,int mouseX,int mouseY) {
        BlockRenderDispatcher renderer = Minecraft.getInstance().getBlockRenderer();
        //PoseStack poseStack1 = RenderSystem.getModelViewStack();
        BlockState blockState = new BlockState(Blocks.STONE, null, null);

        poseStack.pushPose();
        poseStack.translate(mouseX, mouseY, 0);
        renderer.renderSingleBlock(
                blockState,
                poseStack,
                Minecraft.getInstance().renderBuffers().bufferSource(),
                15728880,
                OverlayTexture.NO_OVERLAY,
                EmptyModelData.INSTANCE
        );
        poseStack.popPose();
    }
}
