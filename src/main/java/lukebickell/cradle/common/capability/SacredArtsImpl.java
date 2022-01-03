package lukebickell.cradle.common.capability;

import net.minecraft.nbt.CompoundTag;

public class SacredArtsImpl implements ISacredArts {

    private static final String NBT_KEY_CRADLE_SPIRIT_CORE_SIZE = "cradleSpiritCoreSize";

    private int coreSize = 0;

    @Override
    public int getCoreSize() {
        return this.coreSize;
    }

    @Override
    public void setCoreSize(int size) {
        this.coreSize = coreSize;
        if (this.coreSize < 0) {
            this.coreSize = 0;
        }
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
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.coreSize = nbt.getInt(NBT_KEY_CRADLE_SPIRIT_CORE_SIZE);
    }
}
