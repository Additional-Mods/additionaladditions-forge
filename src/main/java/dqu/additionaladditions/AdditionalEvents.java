package dqu.additionaladditions;

import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.misc.CreativeAdder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
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
                    mc.player.displayClientMessage(MutableComponent.create(new TranslatableContents("depth_meter.elevation", null, new String[]{level})), true);
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = AdditionalAdditions.namespace, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegisterEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("additionalspyglass", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
                gui.setupOverlayRenderState(true, false);
                if (AdditionalAdditions.zoom) {
                    gui.renderSpyglassOverlay(mStack, (float) AdditionalAdditions.spyglassOverlay);
                }
            });
        }
    }

    @Mod.EventBusSubscriber(modid = AdditionalAdditions.namespace, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Events {
        @SubscribeEvent
        public static void registerResourceLoaders(AddReloadListenerEvent event) {
            event.addListener(BehaviourManager.INSTANCE);
        }
    }

    @Mod.EventBusSubscriber(modid = AdditionalAdditions.namespace, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegisterEvents {
        @SubscribeEvent
        public static void creativeTabContents(BuildCreativeModeTabContentsEvent event) {
            var entries = event.getEntries();

            if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
                CreativeAdder.REDSTONE_BLOCKS.pushAllTo(entries);
            }
            if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
                CreativeAdder.FUNCTIONAL_BLOCKS.pushAllTo(entries);
            }
            if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
                CreativeAdder.BUILDING_BLOCKS.pushAllTo(entries);
            }
            if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                CreativeAdder.TOOLS_AND_UTILITIES.pushAllTo(entries);
            }
            if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
                CreativeAdder.INGREDIENTS.pushAllTo(entries);
            }
            if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
                CreativeAdder.FOOD_AND_DRINKS.pushAllTo(entries);
            }
            if (event.getTabKey() == CreativeModeTabs.COMBAT) {
                CreativeAdder.COMBAT.pushAllTo(entries);
            }
        }
    }
}
