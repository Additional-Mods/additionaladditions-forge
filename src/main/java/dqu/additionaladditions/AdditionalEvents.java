package dqu.additionaladditions;

import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
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
import net.minecraftforge.event.AddReloadListenerEvent;
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
            if (!Config.getBool(ConfigValues.DEPTH_METER, "enabled")) return;
            if (mc.player.isHolding(AdditionalRegistry.DEPTH_METER_ITEM.get())) {
                if (Config.getBool(ConfigValues.DEPTH_METER, "displayElevationAlways")) {
                    String level = String.valueOf((int) mc.player.getY());
                    mc.player.displayClientMessage(MutableComponent.create(new TranslatableContents("depth_meter.elevation", level)), true);
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = AdditionalAdditions.namespace, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Events {
        @SubscribeEvent
        public static void registerResourceLoaders(AddReloadListenerEvent event) {
            event.addListener(BehaviourManager.INSTANCE);
        }
    }
}
