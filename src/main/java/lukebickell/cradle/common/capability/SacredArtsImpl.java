package lukebickell.cradle.common.capability;

import lukebickell.cradle.common.ranks.SacredArtsRank;
import net.minecraft.nbt.CompoundTag;

public class SacredArtsImpl implements ISacredArts {

    public static final String NBT_KEY_CRADLE_SPIRIT_CORE_SIZE = "cradleSpiritCoreSize";
    public static final String NBT_KEY_CRADLE_SPIRIT_RANK = "cradleSpiritRank";

    private int coreSize = 0;
    private SacredArtsRank rank = SacredArtsRank.FOUNDATION;

    @Override
    public int getCoreSize() {
        return this.coreSize;
    }

    @Override
    public void setCoreSize(int size) {
        this.coreSize = size;
        if (this.coreSize < 0) {
            this.coreSize = 0;
        }
    }

    @Override
    public SacredArtsRank getRank() {
        return this.rank;
    }

    @Override
    public void setRank(SacredArtsRank rank) {
        this.rank = rank;
    }

    @Override
    public void addOrSubtractToCore(int amount) {
        this.coreSize += amount;
        if (this.coreSize < 0) {
            this.coreSize = 0;
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag tag = new CompoundTag();
        tag.putInt(NBT_KEY_CRADLE_SPIRIT_CORE_SIZE, this.coreSize);
        tag.putString(NBT_KEY_CRADLE_SPIRIT_RANK, this.rank.name());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.coreSize = nbt.getInt(NBT_KEY_CRADLE_SPIRIT_CORE_SIZE);
        this.rank = SacredArtsRank.valueOf(nbt.getString(NBT_KEY_CRADLE_SPIRIT_RANK));
    }
}
