package lukebickell.cradle.common.capability;

import lukebickell.cradle.common.ranks.SacredArtsRank;
import net.minecraft.nbt.CompoundTag;

import static lukebickell.cradle.common.constants.SacredArtsContstants.*;

public class SacredArtsImpl implements ISacredArts {

    private double maxMadra = INITIAL_MAX_MADRA;
    private double currentMadra = 0;
    private SacredArtsRank rank = SacredArtsRank.FOUNDATION;

    @Override
    public double getMaxMadra() {
        return this.maxMadra;
    }

    @Override
    public double getCurrentMadra() {
        return this.currentMadra;
    }

    @Override
    public void advanceToNextRank() {
        switch (rank) {
            case FOUNDATION:
                this.rank = SacredArtsRank.COPPER;
                break;
            case COPPER:
                this.rank = SacredArtsRank.IRON;
                break;
            case IRON:
            case JADE:
                this.rank = SacredArtsRank.JADE;
                break;
            default:
                this.rank = SacredArtsRank.FOUNDATION;
        }
        this.maxMadra = this.calculateMaxMadra();
    }

    private double calculateMaxMadra() {
        return Math.pow(MADRA_ADVANCEMENT_SCALE, MADRA_ADVANCEMENT_INITIAL_BASE + this.rank.order);
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
    public void addOrSubtractToMadra(double amount) {
        this.currentMadra += amount;
        if (this.currentMadra < 0) {
            this.currentMadra = 0;
        }
        if (this.currentMadra >= this.maxMadra) {
            this.currentMadra = this.maxMadra;
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag tag = new CompoundTag();
        tag.putDouble(NBT_KEY_CRADLE_SPIRIT_CURRENT_MADRA, this.currentMadra);
        tag.putDouble(NBT_KEY_CRADLE_SPIRIT_MAX_MADRA, this.maxMadra);
        tag.putString(NBT_KEY_CRADLE_SPIRIT_RANK, this.rank.name());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.currentMadra = nbt.getDouble(NBT_KEY_CRADLE_SPIRIT_CURRENT_MADRA);
        this.maxMadra = nbt.getDouble(NBT_KEY_CRADLE_SPIRIT_MAX_MADRA);
        this.rank = SacredArtsRank.valueOf(nbt.getString(NBT_KEY_CRADLE_SPIRIT_RANK));
    }
}
