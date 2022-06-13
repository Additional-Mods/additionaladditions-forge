package dqu.additionaladditions;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.item.WrenchItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("additionaladditions")
public class AdditionalAdditions {
    public static final String namespace = "additionaladditions";
    public static final Logger LOGGER = LoggerFactory.getLogger(namespace);
    public static boolean zoom = false;
    public static double spyglassOverlay = 0.5f;

    public AdditionalAdditions() {
        if (!Config.initialized) {
            Config.load();
        }

        AdditionalRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        AdditionalRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        AdditionalRegistry.ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        AdditionalRegistry.SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        AdditionalRegistry.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        AdditionalRegistry.POTIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        AdditionalRegistry.POIS.register(FMLJavaModLoadingContext.get().getModEventBus());
        AdditionalRegistry.LOOT_MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(AdditionalAdditionsClient::clientSetup);
    }

    public void setup(final FMLCommonSetupEvent event) {
        if (Config.getBool(ConfigValues.WRENCH)) {
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
        if (Config.getBool(ConfigValues.COMPOSTABLE_ROTTEN_FLESH))
            ComposterBlock.add(0.33f, Items.ROTTEN_FLESH);

        event.enqueueWork(() -> {
            PotionBrewing.addMix(Potions.SWIFTNESS, Items.AMETHYST_SHARD, AdditionalRegistry.HASTE_POTION.get());
            PotionBrewing.addMix(AdditionalRegistry.HASTE_POTION.get(), Items.REDSTONE, AdditionalRegistry.LONG_HASTE_POTION.get());
            PotionBrewing.addMix(AdditionalRegistry.HASTE_POTION.get(), Items.GLOWSTONE_DUST, AdditionalRegistry.STRONG_HASTE_POTION.get());
            PotionBrewing.addMix(Potions.STRONG_SWIFTNESS, Items.AMETHYST_SHARD, AdditionalRegistry.STRONG_HASTE_POTION.get());
            PotionBrewing.addMix(Potions.LONG_SWIFTNESS, Items.AMETHYST_SHARD, AdditionalRegistry.LONG_HASTE_POTION.get());
        });
    }
}
