package dqu.additionaladditions;

import dqu.additionaladditions.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class AdditionalEvents {

    @Mod.EventBusSubscriber(modid = AdditionalAdditions.namespace, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void tickEvent(TickEvent.ClientTickEvent event) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;
            if (!Config.get("DepthMeter")) return;
            if (mc.player.isHolding(AdditionalRegistry.DEPTH_METER_ITEM.get())) {
                String level = String.valueOf((int) mc.player.getY());
                mc.player.displayClientMessage(new TextComponent(level), true);
            }
        }
    }

    @Mod.EventBusSubscriber(modid = AdditionalAdditions.namespace, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Events {
        private static final ResourceLocation ELDER_GUARDIAN_LOOT_TABLE_ID = EntityType.ELDER_GUARDIAN.getDefaultLootTable();
        private static final ResourceLocation ZOMBIE_LOOT_TABLE_ID = EntityType.ZOMBIE.getDefaultLootTable();
        private static final ResourceLocation CREEPER_LOOT_TABLE_ID = EntityType.CREEPER.getDefaultLootTable();
        private static final ResourceLocation PIGLIN_BARTERING_LOOT_TABLE_ID = BuiltInLootTables.PIGLIN_BARTERING;
        private static final ResourceLocation MINESHAFT_CHEST_LOOT_TABLE_ID = BuiltInLootTables.ABANDONED_MINESHAFT;
        private static final ResourceLocation DUNGEON_CHEST_LOOT_TABLE_ID = BuiltInLootTables.SIMPLE_DUNGEON;
        private static final ResourceLocation STRONGHOLD_CHEST_LOOT_TABLE_ID = BuiltInLootTables.STRONGHOLD_CORRIDOR;
        private static final ResourceLocation MANSION_CHEST_LOOT_TABLE_ID = BuiltInLootTables.WOODLAND_MANSION;
        private static final ResourceLocation SHIPWRECK_SUPPLY_CHEST_LOOT_TABLE_ID = BuiltInLootTables.SHIPWRECK_SUPPLY;

        @SubscribeEvent
        public static void modifyLootTables(LootTableLoadEvent event) {
            LootTable table = event.getTable();
            ResourceLocation id = table.getLootTableId();

            if (ELDER_GUARDIAN_LOOT_TABLE_ID.equals(id) && Config.get("TridentShard")) {
                LootPool pool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1f))
                        .add(LootItem.lootTableItem(AdditionalRegistry.TRIDENT_SHARD.get()))
                        .when(LootItemRandomChanceCondition.randomChance(0.33f))
                        .build();
                table.addPool(pool);
            }
            if (DUNGEON_CHEST_LOOT_TABLE_ID.equals(id) || MINESHAFT_CHEST_LOOT_TABLE_ID.equals(id) || STRONGHOLD_CHEST_LOOT_TABLE_ID.equals(id)) {
                if (Config.get("GlowStick")) {
                    LootPool pool = LootPool.lootPool()
                            .setRolls(UniformGenerator.between(0, 4))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
                            .add(LootItem.lootTableItem(AdditionalRegistry.GLOW_STICK_ITEM.get()))
                            .build();
                    table.addPool(pool);
                }
                if (Config.get("Ropes")) {
                    LootPool pool = LootPool.lootPool()
                            .setRolls(UniformGenerator.between(1, 4))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8)))
                            .add(LootItem.lootTableItem(AdditionalRegistry.ROPE_ITEM.get()))
                            .build();
                    table.addPool(pool);
                }
                if (Config.get("DepthMeter")) {
                    LootPool pool = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemRandomChanceCondition.randomChance(0.1f))
                            .add(LootItem.lootTableItem(AdditionalRegistry.DEPTH_METER_ITEM.get()))
                            .build();
                    table.addPool(pool);
                }
            }
            if (DUNGEON_CHEST_LOOT_TABLE_ID.equals(id) || MANSION_CHEST_LOOT_TABLE_ID.equals(id)) {
                if (Config.get("MusicDiscs")) {
                    LootPool pool = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemRandomChanceCondition.randomChance(0.25f))
                            .add(LootItem.lootTableItem(AdditionalRegistry.MUSIC_DISC_0308.get()))
                            .add(LootItem.lootTableItem(AdditionalRegistry.MUSIC_DISC_1007.get()))
                            .add(LootItem.lootTableItem(AdditionalRegistry.MUSIC_DISC_1507.get()))
                            .build();
                    table.addPool(pool);
                }
            }
            if (SHIPWRECK_SUPPLY_CHEST_LOOT_TABLE_ID.equals(id) && Config.get("ShipwreckSpyglassLoot")) {
                LootPool pool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.5f))
                        .add(LootItem.lootTableItem(Items.SPYGLASS))
                        .build();
                table.addPool(pool);
            }
            if (ZOMBIE_LOOT_TABLE_ID.equals(id) || CREEPER_LOOT_TABLE_ID.equals(id)) {
                if (Config.get("ChickenNugget")) {
                    LootPool pool = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemRandomChanceCondition.randomChance(0.025f))
                            .add(LootItem.lootTableItem(AdditionalRegistry.CHICKEN_NUGGET.get()))
                            .build();
                    table.addPool(pool);
                }
            }
            if (PIGLIN_BARTERING_LOOT_TABLE_ID.equals(id) && Config.get("GoldRing")) {
                LootPool pool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.015f))
                        .add(LootItem.lootTableItem(AdditionalRegistry.GOLD_RING.get()))
                        .build();
                table.addPool(pool);
            }

        }
    }
}
