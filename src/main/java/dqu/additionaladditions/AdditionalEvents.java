package dqu.additionaladditions;

import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
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
        private static void addAfter(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries, Item after, Item... item) {
            for (Item i : item) {
                entries.putAfter(new ItemStack(after), new ItemStack(i), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
        private static void addBefore(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries, Item before, Item... item) {
            for (Item i : item) {
                entries.putBefore(new ItemStack(before), new ItemStack(i), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }

        @SubscribeEvent
        public static void creativeTabContents(CreativeModeTabEvent.BuildContents event) {
            var entries = event.getEntries();
            if (event.getTab() == CreativeModeTabs.REDSTONE_BLOCKS) {
                addAfter(entries, Items.REDSTONE, AdditionalRegistry.COPPER_PATINA_ITEM.get());
                addAfter(entries, Items.REDSTONE_LAMP, AdditionalRegistry.AMETHYST_LAMP_ITEM.get());
                addAfter(entries, Items.TARGET, AdditionalRegistry.WRENCH_ITEM.get());
            }
            if (event.getTab() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
                addAfter(entries, Items.REDSTONE_LAMP, AdditionalRegistry.AMETHYST_LAMP_ITEM.get());
            }
            if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
                addAfter(entries, Items.REDSTONE_BLOCK, AdditionalRegistry.PATINA_BLOCK_ITEM.get());
                addAfter(entries, Items.CHAIN, AdditionalRegistry.ROPE_ITEM.get());
            }
            if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                addAfter(entries, Items.SADDLE, AdditionalRegistry.ROPE_ITEM.get());
                addAfter(entries, Items.BONE_MEAL,
                        AdditionalRegistry.WATERING_CAN.get(),
                        AdditionalRegistry.WRENCH_ITEM.get(),
                        AdditionalRegistry.GLOW_STICK_ITEM.get()
                );
                addAfter(entries, Items.CLOCK, AdditionalRegistry.DEPTH_METER_ITEM.get());
                addAfter(entries, Items.ELYTRA, AdditionalRegistry.MYSTERIOUS_BUNDLE_ITEM.get());
                addAfter(entries, Items.SPYGLASS, AdditionalRegistry.POCKET_JUKEBOX_ITEM.get());
                addAfter(entries, Items.GOLDEN_HOE,
                        AdditionalRegistry.ROSE_GOLD_SHOVEL.get(),
                        AdditionalRegistry.ROSE_GOLD_PICKAXE.get(),
                        AdditionalRegistry.ROSE_GOLD_AXE.get(),
                        AdditionalRegistry.ROSE_GOLD_HOE.get()
                );
                addAfter(entries, Items.NETHERITE_HOE,
                        AdditionalRegistry.GILDED_NETHERITE_SHOVEL.get(),
                        AdditionalRegistry.GILDED_NETHERITE_PICKAXE.get(),
                        AdditionalRegistry.GILDED_NETHERITE_AXE.get(),
                        AdditionalRegistry.GILDED_NETHERITE_HOE.get()
                );
                addAfter(entries, Items.MUSIC_DISC_WAIT,
                        AdditionalRegistry.MUSIC_DISC_0308.get(),
                        AdditionalRegistry.MUSIC_DISC_1007.get(),
                        AdditionalRegistry.MUSIC_DISC_1507.get()
                );
            }
            if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
                addBefore(entries, Items.NETHERITE_SCRAP,
                        AdditionalRegistry.GOLD_RING.get(),
                        AdditionalRegistry.ROSE_GOLD_ALLOY.get()
                );
                addAfter(entries, Items.PRISMARINE_CRYSTALS, AdditionalRegistry.TRIDENT_SHARD.get());
            }
            if (event.getTab() == CreativeModeTabs.COMBAT) {
                addAfter(entries, Items.CROSSBOW, AdditionalRegistry.CROSSBOW_WITH_SPYGLASS.get());
                addAfter(entries, Items.GOLDEN_SWORD, AdditionalRegistry.ROSE_GOLD_SWORD.get());
                addAfter(entries, Items.NETHERITE_SWORD, AdditionalRegistry.GILDED_NETHERITE_SWORD.get());
                addAfter(entries, Items.GOLDEN_AXE, AdditionalRegistry.ROSE_GOLD_AXE.get());
                addAfter(entries, Items.NETHERITE_AXE, AdditionalRegistry.GILDED_NETHERITE_AXE.get());
                addAfter(entries, Items.GOLDEN_BOOTS,
                        AdditionalRegistry.ROSE_GOLD_HELMET.get(),
                        AdditionalRegistry.ROSE_GOLD_CHESTPLATE.get(),
                        AdditionalRegistry.ROSE_GOLD_LEGGINGS.get(),
                        AdditionalRegistry.ROSE_GOLD_BOOTS.get()
                );
                addAfter(entries, Items.NETHERITE_BOOTS,
                        AdditionalRegistry.GILDED_NETHERITE_HELMET.get(),
                        AdditionalRegistry.GILDED_NETHERITE_CHESTPLATE.get(),
                        AdditionalRegistry.GILDED_NETHERITE_LEGGINGS.get(),
                        AdditionalRegistry.GILDED_NETHERITE_BOOTS.get()
                );
            }
            if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
                addAfter(entries, Items.COOKED_RABBIT, AdditionalRegistry.FRIED_EGG.get());
                addAfter(entries, Items.PUMPKIN_PIE, AdditionalRegistry.BERRY_PIE.get());
                addAfter(entries, Items.APPLE, AdditionalRegistry.HONEYED_APPLE.get());
                addAfter(entries, Items.ROTTEN_FLESH, AdditionalRegistry.CHICKEN_NUGGET.get());
            }
        }
    }
}
