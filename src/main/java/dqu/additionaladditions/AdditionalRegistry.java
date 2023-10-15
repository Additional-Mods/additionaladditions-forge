package dqu.additionaladditions;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import dqu.additionaladditions.block.CopperPatinaBlock;
import dqu.additionaladditions.block.GlowStickBlock;
import dqu.additionaladditions.block.PatinaBlock;
import dqu.additionaladditions.block.RopeBlock;
import dqu.additionaladditions.enchantment.PrecisionEnchantment;
import dqu.additionaladditions.enchantment.SpeedEnchantment;
import dqu.additionaladditions.entity.GlowStickEntity;
import dqu.additionaladditions.item.*;
import dqu.additionaladditions.material.GildedNetheriteArmorMaterial;
import dqu.additionaladditions.material.GildedNetheriteToolMaterial;
import dqu.additionaladditions.material.RoseGoldArmorMaterial;
import dqu.additionaladditions.material.RoseGoldToolMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@SuppressWarnings("unused")
public class AdditionalRegistry {
    // Registries
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AdditionalAdditions.namespace);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AdditionalAdditions.namespace);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, AdditionalAdditions.namespace);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AdditionalAdditions.namespace);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, AdditionalAdditions.namespace);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, AdditionalAdditions.namespace);
    public static final DeferredRegister<PoiType> POIS = DeferredRegister.create(ForgeRegistries.POI_TYPES, AdditionalAdditions.namespace);
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, AdditionalAdditions.namespace);

    // Items
    public static final RegistryObject<Item> WATERING_CAN = ITEMS.register("watering_can", () -> new WateringCanItem(new Item.Properties().stacksTo(1).durability(101)));
    public static final RegistryObject<Item> WRENCH_ITEM = ITEMS.register("wrench", () -> new WrenchItem(new Item.Properties().stacksTo(1).durability(256)));
    public static final RegistryObject<Item> CROSSBOW_WITH_SPYGLASS = ITEMS.register("crossbow_with_spyglass", () -> new CrossbowItem(new Item.Properties().stacksTo(1).durability(350)));
    public static final RegistryObject<Item> TRIDENT_SHARD = ITEMS.register("trident_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GLOW_STICK_ITEM = ITEMS.register("glow_stick", () -> new GlowStickItem(new Item.Properties()));
    public static final RegistryObject<Item> DEPTH_METER_ITEM = ITEMS.register("depth_meter", () -> new DepthMeterItem(new Item.Properties()));
    public static final RegistryObject<Item> MYSTERIOUS_BUNDLE_ITEM = ITEMS.register("mysterious_bundle", () -> new MysteriousBundleItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GOLD_RING = ITEMS.register("gold_ring", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ROSE_GOLD_ALLOY = ITEMS.register("rose_gold_alloy", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> POCKET_JUKEBOX_ITEM = ITEMS.register("pocket_jukebox", () -> new PocketJukeboxItem(new Item.Properties().stacksTo(1)));

    // Foods
    public static final RegistryObject<Item> FRIED_EGG = ITEMS.register("fried_egg", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.8666f).build())));
    public static final RegistryObject<Item> BERRY_PIE = ITEMS.register("berry_pie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.6f).build())));
    public static final RegistryObject<Item> HONEYED_APPLE = ITEMS.register("honeyed_apple", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(1.6f).build())));
    public static final RegistryObject<Item> CHICKEN_NUGGET = ITEMS.register("chicken_nugget", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.9f).meat().build())));

    // Armor
    public static final ArmorMaterial ROSE_GOLD_ARMOR_MATERIAL = new RoseGoldArmorMaterial();
    public static final ArmorMaterial GILDED_NETHERITE_ARMOR_MATERIAL = new GildedNetheriteArmorMaterial();
    public static final RegistryObject<Item> ROSE_GOLD_UPGRADE = ITEMS.register("rose_gold_upgrade", () -> AdditionalSmithingTemplate.create("rose_gold_upgrade", AdditionalSmithingTemplate.iconsEquipment(), List.of(AdditionalSmithingTemplate.SmithingIcon.ALLOY.location())));
    public static final RegistryObject<Item> GILDED_NETHERITE_UPGRADE = ITEMS.register("gilded_netherite_upgrade", () -> AdditionalSmithingTemplate.create("gilded_netherite_upgrade", AdditionalSmithingTemplate.iconsEquipment(), List.of(AdditionalSmithingTemplate.SmithingIcon.RING.location())));

    public static final RegistryObject<Item> ROSE_GOLD_HELMET = ITEMS.register("rose_gold_helmet", () -> new AdditionalArmorItem(ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ROSE_GOLD_CHESTPLATE = ITEMS.register("rose_gold_chestplate", () -> new AdditionalArmorItem(ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ROSE_GOLD_LEGGINGS = ITEMS.register("rose_gold_leggings", () -> new AdditionalArmorItem(ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ROSE_GOLD_BOOTS = ITEMS.register("rose_gold_boots", () -> new AdditionalArmorItem(ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> GILDED_NETHERITE_HELMET = ITEMS.register("gilded_netherite_helmet", () -> new AdditionalArmorItem(GILDED_NETHERITE_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> GILDED_NETHERITE_CHESTPLATE = ITEMS.register("gilded_netherite_chestplate", () -> new AdditionalArmorItem(GILDED_NETHERITE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> GILDED_NETHERITE_LEGGINGS = ITEMS.register("gilded_netherite_leggings", () -> new AdditionalArmorItem(GILDED_NETHERITE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> GILDED_NETHERITE_BOOTS = ITEMS.register("gilded_netherite_boots", () -> new AdditionalArmorItem(GILDED_NETHERITE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties().fireResistant()));

    // Tools
    public static final RegistryObject<Item> ROSE_GOLD_SWORD = ITEMS.register("rose_gold_sword", () -> new AdditionalSwordItem(RoseGoldToolMaterial.MATERIAL, 4, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> ROSE_GOLD_AXE = ITEMS.register("rose_gold_axe", () -> new AdditionalAxeItem(RoseGoldToolMaterial.MATERIAL, 6, -3.1F, new Item.Properties()));
    public static final RegistryObject<Item> ROSE_GOLD_PICKAXE = ITEMS.register("rose_gold_pickaxe", () -> new AdditionalPickaxeItem(RoseGoldToolMaterial.MATERIAL, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> ROSE_GOLD_SHOVEL = ITEMS.register("rose_gold_shovel", () -> new AdditionalShovelItem(RoseGoldToolMaterial.MATERIAL, 1.5F, -3F, new Item.Properties()));
    public static final RegistryObject<Item> ROSE_GOLD_HOE = ITEMS.register("rose_gold_hoe", () -> new AdditionalHoeItem(RoseGoldToolMaterial.MATERIAL, -2, -1F, new Item.Properties()));
    public static final RegistryObject<Item> GILDED_NETHERITE_SWORD = ITEMS.register("gilded_netherite_sword", () -> new AdditionalSwordItem(GildedNetheriteToolMaterial.MATERIAL, 5, -2.4F, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> GILDED_NETHERITE_AXE = ITEMS.register("gilded_netherite_axe", () -> new AdditionalAxeItem(GildedNetheriteToolMaterial.MATERIAL, 7, -3F, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> GILDED_NETHERITE_PICKAXE = ITEMS.register("gilded_netherite_pickaxe", () -> new AdditionalPickaxeItem(GildedNetheriteToolMaterial.MATERIAL, 3, -2.6F, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> GILDED_NETHERITE_SHOVEL = ITEMS.register("gilded_netherite_shovel", () -> new AdditionalShovelItem(GildedNetheriteToolMaterial.MATERIAL, 3.5F, -3F, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> GILDED_NETHERITE_HOE = ITEMS.register("gilded_netherite_hoe", () -> new AdditionalHoeItem(GildedNetheriteToolMaterial.MATERIAL, -2, 0, new Item.Properties().fireResistant()));

    // Blocks
    public static final RegistryObject<Block> GLOW_STICK_BLOCK = BLOCKS.register("glow_stick", () -> new GlowStickBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).pushReaction(PushReaction.DESTROY).noCollission().lightLevel((state) -> 12).instabreak()));
    public static final RegistryObject<Block> ROPE_BLOCK = BLOCKS.register("rope", () -> new RopeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.WOOL).instabreak()));
    public static final RegistryObject<Block> COPPER_PATINA = BLOCKS.register("copper_patina", () -> new CopperPatinaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.TUFF).instabreak()));
    public static final RegistryObject<Block> AMETHYST_LAMP = BLOCKS.register("amethyst_lamp", () -> new RedstoneLampBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).pushReaction(PushReaction.NORMAL).sound(SoundType.GLASS).strength(0.3f)));
    public static final RegistryObject<Block> PATINA_BLOCK = BLOCKS.register("patina_block", () -> new PatinaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.NORMAL).sound(SoundType.ROOTED_DIRT).strength(0.5f)));

    // Block Items
    public static final RegistryObject<Item> COPPER_PATINA_ITEM = ITEMS.register("copper_patina", () -> new CopperPatinaItem(COPPER_PATINA.get(), new Item.Properties()));
    public static final RegistryObject<Item> ROPE_ITEM = ITEMS.register("rope", () -> new BlockItem(ROPE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> AMETHYST_LAMP_ITEM = ITEMS.register("amethyst_lamp", () -> new BlockItem(AMETHYST_LAMP.get(), new Item.Properties()));
    public static final RegistryObject<Item> PATINA_BLOCK_ITEM = ITEMS.register("patina_block", () -> new BlockItem(PATINA_BLOCK.get(), new Item.Properties()));

    // Enchantments
    public static final RegistryObject<Enchantment> ENCHANTMENT_PRECISION = ENCHANTMENTS.register("precision", PrecisionEnchantment::new);
    public static final RegistryObject<Enchantment> ENCHANTMENT_SPEED = ENCHANTMENTS.register("speed", SpeedEnchantment::new);

    // Entities
    public static final RegistryObject<EntityType<?>> GLOW_STICK_ENTITY = ENTITIES.register("glow_stick", () ->
            EntityType.Builder.<GlowStickEntity>of(GlowStickEntity::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .setTrackingRange(4).setTrackingRange(10).build("glow_stick")
            );

    // Sound Events
    private static final SoundEvent U_SOUND_EVENT_0308 = SoundEvent.createVariableRangeEvent(new ResourceLocation(AdditionalAdditions.namespace, "0308"));
    private static final SoundEvent U_SOUND_EVENT_1007 = SoundEvent.createVariableRangeEvent(new ResourceLocation(AdditionalAdditions.namespace, "1007"));
    private static final SoundEvent U_SOUND_EVENT_1507 = SoundEvent.createVariableRangeEvent(new ResourceLocation(AdditionalAdditions.namespace, "1507"));

    public static final RegistryObject<SoundEvent> SOUND_EVENT_0308 = SOUND_EVENTS.register("0308", () -> U_SOUND_EVENT_0308);
    public static final RegistryObject<SoundEvent> SOUND_EVENT_1007 = SOUND_EVENTS.register("1007", () -> U_SOUND_EVENT_1007);
    public static final RegistryObject<SoundEvent> SOUND_EVENT_1507 = SOUND_EVENTS.register("1507", () -> U_SOUND_EVENT_1507);

    // Music Disc Items
    public static final RegistryObject<Item> MUSIC_DISC_0308 = ITEMS.register("music_disc_0308", () -> new AdditionalMusicDiscItem(15, U_SOUND_EVENT_0308, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 83));
    public static final RegistryObject<Item> MUSIC_DISC_1007 = ITEMS.register("music_disc_1007", () -> new AdditionalMusicDiscItem(15, U_SOUND_EVENT_1007, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 102));
    public static final RegistryObject<Item> MUSIC_DISC_1507 = ITEMS.register("music_disc_1507", () -> new AdditionalMusicDiscItem(15, U_SOUND_EVENT_1507, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 214));

    // Potions
    public static final RegistryObject<Potion> HASTE_POTION = POTIONS.register("haste_potion", () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 3400)));
    public static final RegistryObject<Potion> LONG_HASTE_POTION = POTIONS.register("long_haste_potion", () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 6800)));
    public static final RegistryObject<Potion> STRONG_HASTE_POTION = POTIONS.register("strong_haste_potion", () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 1600, 1)));

    // POIs
    public static ResourceLocation AMETHYST_LAMP_POI_RL = new ResourceLocation(AdditionalAdditions.namespace, "amethyst_lamp_poi");
    public static final RegistryObject<PoiType> AMETHYST_LAMP_POI = POIS.register(AMETHYST_LAMP_POI_RL.getPath(), () -> new PoiType(
            ImmutableSet.copyOf(AMETHYST_LAMP.get().getStateDefinition().getPossibleStates().stream().filter(state -> state.getValue(BlockStateProperties.LIT)).toList()),
            0, 8
    ));

    // Loot Modifier Serializers
    public static final RegistryObject<Codec<AdditionalLootModifier>> ADDITIONAL_LOOT_MODIFIER = LOOT_MODIFIERS.register("additional_loot_modifier", AdditionalLootModifier.CODEC);
}
