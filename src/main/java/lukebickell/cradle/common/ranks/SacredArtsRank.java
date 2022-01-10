package lukebickell.cradle.common.ranks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public enum SacredArtsRank {
    FOUNDATION(1, Blocks.STONE),
    COPPER(2, Blocks.COPPER_BLOCK),
    IRON(3, Blocks.IRON_BLOCK),
    JADE(4, Blocks.EMERALD_BLOCK);

    public Block block;
    public int order;

    SacredArtsRank(int order, Block block) {
        this.order = order;
        this.block = block;
    }
}
