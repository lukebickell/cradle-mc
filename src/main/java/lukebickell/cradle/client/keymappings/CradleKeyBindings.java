package lukebickell.cradle.client.keymappings;

import com.mojang.blaze3d.platform.InputConstants;
import lukebickell.cradle.Cradle;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jline.keymap.KeyMap;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Cradle.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CradleKeyBindings {

    private static final String CATEGORY = "key.category.cradle.general";

    public static final KeyMapping CYCLE_MADRA = new KeyMapping("key.cradle.cycle_madra", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, CATEGORY);


    @SubscribeEvent
    public static void registerKeyMappings(final FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(CYCLE_MADRA);
    }
}
