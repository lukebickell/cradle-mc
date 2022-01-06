package lukebickell.cradle.common.capability;

import lukebickell.cradle.common.ranks.SacredArtsRank;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISacredArts extends INBTSerializable<CompoundTag> {

    public int getCoreSize();
    public void setCoreSize(int size);
    public void addOrSubtractToCore(int amount);

    public SacredArtsRank getRank();
    public void setRank(SacredArtsRank rank);
}
