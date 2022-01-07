package lukebickell.cradle.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import lukebickell.cradle.client.ClientSacredArts;
import lukebickell.cradle.common.ranks.SacredArtsRank;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SacredArtsOverview extends GuiComponent {

    private final ClientSacredArts sacredArts;
    private final SacredArtsCoreWidget coreWidget;

    public SacredArtsOverview(ClientSacredArts sacredArts) {
        this.sacredArts = sacredArts;
        this.coreWidget = new SacredArtsCoreWidget(this.getBlockFromRank(sacredArts.getRank()));
    }

    public void drawContents(PoseStack poseStack, int innerWidth, int innerHeight) {
        int centerX = innerWidth / 2;
        int centerY = innerHeight / 2;

        poseStack.pushPose();
        poseStack.translate(0, 0, 950);
        this.coreWidget.draw(centerX, centerY, 56, SacredArtsCoreWidget.Speed.FAST);
        poseStack.popPose();
    }

    public Block getBlockFromRank(SacredArtsRank rank) {
        switch (rank) {
            case FOUNDATION:
                return Blocks.STONE;
            case IRON:
                return Blocks.IRON_BLOCK;
            case COPPER:
                return Blocks.COPPER_BLOCK;
            case JADE:
                return Blocks.EMERALD_BLOCK;
            default:
                return Blocks.BELL;
        }
    }
}
