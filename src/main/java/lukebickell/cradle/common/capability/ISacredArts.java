package lukebickell.cradle.common.capability;

import lukebickell.cradle.common.ranks.SacredArtsRank;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISacredArts extends INBTSerializable<CompoundTag> {

    public double getMaxMadra();
    public double getCurrentMadra();
    public void advanceToNextRank();
    public void addOrSubtractToMadra(double amount);

    public SacredArtsRank getRank();
    public void setRank(SacredArtsRank rank);
}
