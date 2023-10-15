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
import net.minecraft.world.level.storage.loot.LootTable;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AdditionalLootModifier extends LootModifier {
    private static final List<LootEntry> entries = new ArrayList<>();

    protected final LootItemCondition[] conditions;

    public static final Supplier<Codec<AdditionalLootModifier>> CODEC = Suppliers.memoize(() ->
        RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, AdditionalLootModifier::new)));

    public AdditionalLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
        this.conditions = conditionsIn;

        register(EntityType.ELDER_GUARDIAN.getDefaultLootTable(), () -> Config.getBool(ConfigValues.TRIDENT_SHARD), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(AdditionalRegistry.TRIDENT_SHARD.get()))
                .when(LootItemRandomChanceCondition.randomChance(0.33f))
        );
        register(List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.STRONGHOLD_CORRIDOR), () -> Config.getBool(ConfigValues.GLOW_STICK), LootPool.lootPool()
                .setRolls(UniformGenerator.between(0, 4))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
                .add(LootItem.lootTableItem(AdditionalRegistry.GLOW_STICK_ITEM.get()))
        );
        register(List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.STRONGHOLD_CORRIDOR), () -> Config.getBool(ConfigValues.DEPTH_METER), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.1f))
                .add(LootItem.lootTableItem(AdditionalRegistry.DEPTH_METER_ITEM.get()))
        );
        register(List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.WOODLAND_MANSION), () -> Config.getBool(ConfigValues.MUSIC_DISCS), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.25f))
                .add(LootItem.lootTableItem(AdditionalRegistry.MUSIC_DISC_0308.get()))
                .add(LootItem.lootTableItem(AdditionalRegistry.MUSIC_DISC_1007.get()))
                .add(LootItem.lootTableItem(AdditionalRegistry.MUSIC_DISC_1507.get()))
        );
        register(BuiltInLootTables.SHIPWRECK_SUPPLY, () -> Config.getBool(ConfigValues.SHIPWRECK_SPYGLASS_LOOT), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.5f))
                .add(LootItem.lootTableItem(Items.SPYGLASS))
        );
        register(List.of(EntityType.ZOMBIE.getDefaultLootTable(), EntityType.CREEPER.getDefaultLootTable()), () -> Config.getBool(ConfigValues.CHICKEN_NUGGET), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025f, 0.01f))
                .add(LootItem.lootTableItem(AdditionalRegistry.CHICKEN_NUGGET.get()))
        );
        register(BuiltInLootTables.PIGLIN_BARTERING, () -> Config.getBool(ConfigValues.GILDED_NETHERITE), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.015f))
                .add(LootItem.lootTableItem(AdditionalRegistry.GOLD_RING.get()))
        );
        register(List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.STRONGHOLD_CORRIDOR), () -> Config.getBool(ConfigValues.ROPES), LootPool.lootPool()
                .setRolls(UniformGenerator.between(1, 4))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8)))
                .add(LootItem.lootTableItem(AdditionalRegistry.ROPE_BLOCK.get()))
        );
        register(List.of(BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.PILLAGER_OUTPOST, BuiltInLootTables.UNDERWATER_RUIN_SMALL, BuiltInLootTables.UNDERWATER_RUIN_BIG, BuiltInLootTables.SHIPWRECK_TREASURE), () -> Config.getBool(ConfigValues.ROSE_GOLD), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.32f))
                .add(LootItem.lootTableItem(AdditionalRegistry.ROSE_GOLD_UPGRADE.get()))
        );
        register(BuiltInLootTables.RUINED_PORTAL, () -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.45f))
                .add(LootItem.lootTableItem(AdditionalRegistry.GILDED_NETHERITE_UPGRADE.get()))
        );


        postInit();
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ResourceLocation id = context.getQueriedLootTableId();

        return handle(id, generatedLoot, context);
    }

    public static void register(ResourceLocation table, Supplier<Boolean> condition, LootPool.Builder pool) {
        LootEntry entry = new LootEntry(table, condition, pool);
        entries.add(entry);
    }

    public static void register(List<ResourceLocation> tables, Supplier<Boolean> condition, LootPool.Builder pool) {
        for (ResourceLocation table : tables) {
            register(table, condition, pool);
        }
    }

    private static void postInit() {
        AdditionalAdditions.LOGGER.info("[" + AdditionalAdditions.namespace + "] Adding " + entries.size() + " loot pools");
    }

    private static ObjectArrayList<ItemStack> handle(ResourceLocation id, ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (LootEntry entry : entries) {
            if (entry.table.equals(id)) {
                if (!entry.condition.get()) continue;

                LootPool pool = entry.pool.build();
                pool.addRandomItems(generatedLoot::add, context);
            }
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

    private record LootEntry(
            ResourceLocation table,
            Supplier<Boolean> condition,
            LootPool.Builder pool
    ) {}
}
