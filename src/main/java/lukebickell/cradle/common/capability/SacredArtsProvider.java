package lukebickell.cradle.common.capability;

import lukebickell.cradle.Cradle;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SacredArtsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final ResourceLocation CRADLE_SACRED_ARTS_LOCATION = new ResourceLocation(Cradle.MODID, "cradle_sacred_arts");
    private final ISacredArts backend = new SacredArtsImpl();
    private final LazyOptional<ISacredArts> sacredArtsOptional = LazyOptional.of(() -> backend);

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return SacredArtsCapability.INSTANCE.orEmpty(cap, this.sacredArtsOptional);
    }

    void invalidate() {
        this.sacredArtsOptional.invalidate();
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.backend.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.backend.deserializeNBT(nbt);
    }

    @Mod.EventBusSubscriber(modid = Cradle.MODID, bus = Bus.FORGE)
    private static class EventHandler {
        @SubscribeEvent
        public static void onAttachEntityCaps(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) event.addCapability(SacredArtsProvider.CRADLE_SACRED_ARTS_LOCATION, new SacredArtsProvider());
        }

        @SubscribeEvent
        public static void onPlayerClone(PlayerEvent.Clone event) {
            event.getOriginal().reviveCaps(); // IDK why I need to do this, but the original capability isn't present otherwise
            event.getOriginal().getCapability(SacredArtsCapability.INSTANCE).ifPresent(cap -> {
                event.getPlayer().getCapability(SacredArtsCapability.INSTANCE).ifPresent(c -> c.deserializeNBT(cap.serializeNBT()));
            });
        }
    }
}
