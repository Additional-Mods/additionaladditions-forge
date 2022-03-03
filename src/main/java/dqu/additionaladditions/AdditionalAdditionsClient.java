package dqu.additionaladditions;

import dqu.additionaladditions.item.PocketJukeboxItem;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = AdditionalAdditions.namespace, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AdditionalAdditionsClient {
    public static final IIngameOverlay ADDITIONAL_SPYGLASS_OVERLAY = OverlayRegistry.registerOverlayTop("AdditionalSpyglass", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
        gui.setupOverlayRenderState(true, false);
        if (AdditionalAdditions.zoom) gui.renderSpyglassOverlay((float) AdditionalAdditions.spyglassOverlay);
    });

    public static void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(AdditionalRegistry.COPPER_PATINA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AdditionalRegistry.ROPE_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AdditionalRegistry.GLOW_STICK_BLOCK.get(), RenderType.cutout());

        event.enqueueWork(() -> {
            ItemProperties.register(AdditionalRegistry.CROSSBOW_WITH_SPYGLASS.get(), new ResourceLocation(AdditionalAdditions.namespace, "pull"), (stack, level, living, id) -> {
                if (living == null) return 0.0F;
                return living.getUseItem() != stack ? 0.0F : (stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20.0F;
            });

            ItemProperties.register(AdditionalRegistry.CROSSBOW_WITH_SPYGLASS.get(), new ResourceLocation(AdditionalAdditions.namespace, "pulling"), (stack, level, living, id) -> {
                if (living == null) return 0.0F;
                return living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F;
            });

            ItemProperties.register(AdditionalRegistry.CROSSBOW_WITH_SPYGLASS.get(), new ResourceLocation(AdditionalAdditions.namespace, "charged"), (stack, level, living, id) -> {
                return CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
            });

            ItemProperties.register(AdditionalRegistry.CROSSBOW_WITH_SPYGLASS.get(), new ResourceLocation(AdditionalAdditions.namespace, "firework"), (stack, level, living, id) -> {
                return CrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
            });

            ItemProperties.register(AdditionalRegistry.POCKET_JUKEBOX_ITEM.get(), new ResourceLocation(AdditionalAdditions.namespace, "disc"), (stack, level, living, id) -> {
                return PocketJukeboxItem.hasDisc(stack) ? 1.0F : 0.0F;
            });

            ItemProperties.register(AdditionalRegistry.DEPTH_METER_ITEM.get(), new ResourceLocation(AdditionalAdditions.namespace, "angle"), (stack, level, living, id) -> {
                if (living == null) return 0.2f;
                int height = living.level.getMaxBuildHeight() - living.level.getMinBuildHeight();
                double playerY = living.getY() - living.level.getMinBuildHeight();
                double quarter = height / 4d;
                int value = (int) (playerY / quarter);
                return (value < 0) ? 0 : (Math.min(value, 3) / 10f);
            });
        });
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((EntityType<? extends ThrowableItemProjectile>) AdditionalRegistry.GLOW_STICK_ENTITY.get(), ThrownItemRenderer::new);
    }
}
