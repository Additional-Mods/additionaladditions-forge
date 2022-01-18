package dqu.additionaladditions;

import dqu.additionaladditions.config.Config;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("additionaladditions")
public class AdditionalAdditions {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String namespace = "additionaladditions";
    public static boolean zoom = false;
    public static double spyglassOverlay = 0.5f;

    public AdditionalAdditions() {
        if (!Config.initialized) {
            Config.init();
            Config.load();
        }

        AdditionalRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        AdditionalRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        AdditionalRegistry.ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        AdditionalRegistry.SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        AdditionalRegistry.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        AdditionalRegistry.POTIONS.register(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(AdditionalAdditionsClient::clientSetup);
    }

    public void setup(final FMLCommonSetupEvent event) {
        return;
    }
}
