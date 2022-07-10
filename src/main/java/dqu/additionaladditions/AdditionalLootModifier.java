package dqu.additionaladditions;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AdditionalLootModifier extends LootModifier {
    private static final ResourceLocation ELDER_GUARDIAN_LOOT_TABLE_ID = EntityType.ELDER_GUARDIAN.getDefaultLootTable();
    private static final ResourceLocation ZOMBIE_LOOT_TABLE_ID = EntityType.ZOMBIE.getDefaultLootTable();
    private static final ResourceLocation CREEPER_LOOT_TABLE_ID = EntityType.CREEPER.getDefaultLootTable();
    private static final ResourceLocation PIGLIN_BARTERING_LOOT_TABLE_ID = BuiltInLootTables.PIGLIN_BARTERING;
    private static final ResourceLocation MINESHAFT_CHEST_LOOT_TABLE_ID = BuiltInLootTables.ABANDONED_MINESHAFT;
    private static final ResourceLocation DUNGEON_CHEST_LOOT_TABLE_ID = BuiltInLootTables.SIMPLE_DUNGEON;
    private static final ResourceLocation STRONGHOLD_CHEST_LOOT_TABLE_ID = BuiltInLootTables.STRONGHOLD_CORRIDOR;
    private static final ResourceLocation MANSION_CHEST_LOOT_TABLE_ID = BuiltInLootTables.WOODLAND_MANSION;
    private static final ResourceLocation SHIPWRECK_SUPPLY_CHEST_LOOT_TABLE_ID = BuiltInLootTables.SHIPWRECK_SUPPLY;

    protected final LootItemCondition[] conditions;

    public static final Supplier<Codec<AdditionalLootModifier>> CODEC = Suppliers.memoize(() ->
        RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, AdditionalLootModifier::new)));

    public AdditionalLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
        this.conditions = conditionsIn;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ResourceLocation id = context.getQueriedLootTableId();

        if (ELDER_GUARDIAN_LOOT_TABLE_ID.equals(id) && Config.getBool(ConfigValues.TRIDENT_SHARD)) {
            LootPool pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1f))
                    .add(LootItem.lootTableItem(AdditionalRegistry.TRIDENT_SHARD.get()))
                    .when(LootItemRandomChanceCondition.randomChance(0.33f))
                    .build();
            pool.addRandomItems(generatedLoot::add, context);
        }
        if (DUNGEON_CHEST_LOOT_TABLE_ID.equals(id) || MINESHAFT_CHEST_LOOT_TABLE_ID.equals(id) || STRONGHOLD_CHEST_LOOT_TABLE_ID.equals(id)) {
            if (Config.getBool(ConfigValues.GLOW_STICK)) {
                LootPool pool = LootPool.lootPool()
                        .setRolls(UniformGenerator.between(0, 4))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
                        .add(LootItem.lootTableItem(AdditionalRegistry.GLOW_STICK_ITEM.get()))
                        .build();
                pool.addRandomItems(generatedLoot::add, context);
            }
            if (Config.getBool(ConfigValues.ROPES)) {
                LootPool pool = LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1, 4))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8)))
                        .add(LootItem.lootTableItem(AdditionalRegistry.ROPE_ITEM.get()))
                        .build();
                pool.addRandomItems(generatedLoot::add, context);
            }
            if (Config.getBool(ConfigValues.DEPTH_METER, "enabled")) {
                LootPool pool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                        .add(LootItem.lootTableItem(AdditionalRegistry.DEPTH_METER_ITEM.get()))
                        .build();
                pool.addRandomItems(generatedLoot::add, context);
            }
        }
        if (DUNGEON_CHEST_LOOT_TABLE_ID.equals(id) || MANSION_CHEST_LOOT_TABLE_ID.equals(id)) {
            if (Config.getBool(ConfigValues.MUSIC_DISCS)) {
                LootPool pool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(LootItem.lootTableItem(AdditionalRegistry.MUSIC_DISC_0308.get()))
                        .add(LootItem.lootTableItem(AdditionalRegistry.MUSIC_DISC_1007.get()))
                        .add(LootItem.lootTableItem(AdditionalRegistry.MUSIC_DISC_1507.get()))
                        .build();
                pool.addRandomItems(generatedLoot::add, context);
            }
        }
        if (SHIPWRECK_SUPPLY_CHEST_LOOT_TABLE_ID.equals(id) && Config.getBool(ConfigValues.SHIPWRECK_SPYGLASS_LOOT)) {
            LootPool pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(0.5f))
                    .add(LootItem.lootTableItem(Items.SPYGLASS))
                    .build();
            pool.addRandomItems(generatedLoot::add, context);
        }
        if (ZOMBIE_LOOT_TABLE_ID.equals(id) || CREEPER_LOOT_TABLE_ID.equals(id)) {
            if (Config.getBool(ConfigValues.CHICKEN_NUGGET)) {
                LootPool pool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025f, 0.1f))
                        .add(LootItem.lootTableItem(AdditionalRegistry.CHICKEN_NUGGET.get()))
                        .build();
                pool.addRandomItems(generatedLoot::add, context);
            }
        }
        if (PIGLIN_BARTERING_LOOT_TABLE_ID.equals(id) && Config.getBool(ConfigValues.GOLD_RING)) {
            LootPool pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(0.015f))
                    .add(LootItem.lootTableItem(AdditionalRegistry.GOLD_RING.get()))
                    .build();
            pool.addRandomItems(generatedLoot::add, context);
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
