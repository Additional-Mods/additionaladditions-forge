package dqu.additionaladditions;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.item.WrenchItem;
import dqu.additionaladditions.misc.CreativeAdder;
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

        event.enqueueWork(() -> {
            // Blocks
            CreativeAdder.REDSTONE_BLOCKS.add(() -> Config.getBool(ConfigValues.COPPER_PATINA), Items.REDSTONE_BLOCK, AdditionalRegistry.COPPER_PATINA.get());
            CreativeAdder.REDSTONE_BLOCKS.add(() -> Config.getBool(ConfigValues.AMETHYST_LAMP, "enabled"), Items.REDSTONE_LAMP, AdditionalRegistry.AMETHYST_LAMP.get());
            CreativeAdder.FUNCTIONAL_BLOCKS.add(() -> Config.getBool(ConfigValues.AMETHYST_LAMP, "enabled"), Items.REDSTONE_LAMP, AdditionalRegistry.AMETHYST_LAMP.get());
            CreativeAdder.BUILDING_BLOCKS.add(() -> Config.getBool(ConfigValues.COPPER_PATINA), Items.REDSTONE_BLOCK, AdditionalRegistry.PATINA_BLOCK.get());
            CreativeAdder.BUILDING_BLOCKS.add(() -> Config.getBool(ConfigValues.ROPES), Items.CHAIN, AdditionalRegistry.ROPE_BLOCK.get());
            CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.ROPES), Items.SPYGLASS, AdditionalRegistry.ROPE_BLOCK.get());

            // Items
            CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.WATERING_CAN), Items.BONE_MEAL, AdditionalRegistry.WATERING_CAN.get());
            CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.WRENCH), Items.BONE_MEAL, AdditionalRegistry.WRENCH_ITEM.get());
            CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.GLOW_STICK), Items.BONE_MEAL, AdditionalRegistry.GLOW_STICK_ITEM.get());
            CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.DEPTH_METER, "enabled"), Items.CLOCK, AdditionalRegistry.DEPTH_METER_ITEM.get());
            CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.MYSTERIOUS_BUNDLE), Items.ELYTRA, AdditionalRegistry.MYSTERIOUS_BUNDLE_ITEM.get());
            CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.POCKET_JUKEBOX), Items.SPYGLASS, AdditionalRegistry.POCKET_JUKEBOX_ITEM.get());
            CreativeAdder.INGREDIENTS.addBefore(() -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), Items.NETHERITE_SCRAP, AdditionalRegistry.GOLD_RING.get());
            CreativeAdder.INGREDIENTS.addBefore(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.NETHERITE_SCRAP, AdditionalRegistry.ROSE_GOLD_ALLOY.get());
            CreativeAdder.INGREDIENTS.add(() -> Config.getBool(ConfigValues.TRIDENT_SHARD), Items.PRISMARINE_CRYSTALS, AdditionalRegistry.TRIDENT_SHARD.get());
            CreativeAdder.REDSTONE_BLOCKS.add(() -> Config.getBool(ConfigValues.WRENCH), Items.TARGET, AdditionalRegistry.WRENCH_ITEM.get());
            CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.CROSSBOWS), Items.CROSSBOW, AdditionalRegistry.CROSSBOW_WITH_SPYGLASS.get());
            CreativeAdder.FOOD_AND_DRINKS.add(() -> Config.getBool(ConfigValues.FOOD, "FriedEgg"), Items.COOKED_RABBIT, AdditionalRegistry.FRIED_EGG.get());
            CreativeAdder.FOOD_AND_DRINKS.add(() -> Config.getBool(ConfigValues.FOOD, "BerryPie"), Items.PUMPKIN_PIE, AdditionalRegistry.BERRY_PIE.get());
            CreativeAdder.FOOD_AND_DRINKS.add(() -> Config.getBool(ConfigValues.FOOD, "HoneyedApple"), Items.APPLE, AdditionalRegistry.HONEYED_APPLE.get());
            CreativeAdder.FOOD_AND_DRINKS.add(() -> Config.getBool(ConfigValues.CHICKEN_NUGGET), Items.ROTTEN_FLESH, AdditionalRegistry.CHICKEN_NUGGET.get());

            // Materials Tools Armor
            CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_HOE, AdditionalRegistry.ROSE_GOLD_SHOVEL.get(), AdditionalRegistry.ROSE_GOLD_PICKAXE.get(), AdditionalRegistry.ROSE_GOLD_AXE.get(), AdditionalRegistry.ROSE_GOLD_HOE.get());
            CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), Items.NETHERITE_HOE, AdditionalRegistry.GILDED_NETHERITE_SHOVEL.get(), AdditionalRegistry.GILDED_NETHERITE_PICKAXE.get(), AdditionalRegistry.GILDED_NETHERITE_AXE.get(), AdditionalRegistry.GILDED_NETHERITE_HOE.get());
            CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_SWORD, AdditionalRegistry.ROSE_GOLD_SWORD.get());
            CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_AXE, AdditionalRegistry.ROSE_GOLD_AXE.get());
            CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_BOOTS, AdditionalRegistry.ROSE_GOLD_HELMET.get(), AdditionalRegistry.ROSE_GOLD_CHESTPLATE.get(), AdditionalRegistry.ROSE_GOLD_LEGGINGS.get(), AdditionalRegistry.ROSE_GOLD_BOOTS.get());
            CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), Items.NETHERITE_SWORD, AdditionalRegistry.GILDED_NETHERITE_SWORD.get());
            CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), Items.NETHERITE_AXE, AdditionalRegistry.GILDED_NETHERITE_AXE.get());
            CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), Items.NETHERITE_BOOTS, AdditionalRegistry.GILDED_NETHERITE_HELMET.get(), AdditionalRegistry.GILDED_NETHERITE_CHESTPLATE.get(), AdditionalRegistry.GILDED_NETHERITE_LEGGINGS.get(), AdditionalRegistry.GILDED_NETHERITE_BOOTS.get());
            CreativeAdder.INGREDIENTS.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, AdditionalRegistry.ROSE_GOLD_UPGRADE.get());
            CreativeAdder.INGREDIENTS.add(() -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, AdditionalRegistry.GILDED_NETHERITE_UPGRADE.get());

            // Music Discs
            CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.MUSIC_DISCS), Items.MUSIC_DISC_WAIT, AdditionalRegistry.MUSIC_DISC_0308.get(), AdditionalRegistry.MUSIC_DISC_1007.get(), AdditionalRegistry.MUSIC_DISC_1507.get());


        });
    }
}
