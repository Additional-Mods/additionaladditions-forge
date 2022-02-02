package dqu.additionaladditions;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.item.WrenchItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
        if (Config.get("Wrench")) {
            DispenserBlock.registerBehavior(AdditionalRegistry.WRENCH_ITEM.get(), new DefaultDispenseItemBehavior() {
                public ItemStack execute(BlockSource pointer, ItemStack stack) {
                    WrenchItem wrench = (WrenchItem) stack.getItem();

                    BlockState dstate = pointer.getBlockState();
                    BlockPos pos = pointer.getPos().relative(dstate.getValue(BlockStateProperties.FACING));
                    BlockState state = pointer.getLevel().getBlockState(pos);

                    wrench.dispenserUse(pointer.getLevel(), pos, state, stack);
                    return stack;
                }
            });
        }
        if (Config.get("CompostableRottenFlesh"))
            ComposterBlock.add(0.33f, Items.ROTTEN_FLESH);
    }
}
