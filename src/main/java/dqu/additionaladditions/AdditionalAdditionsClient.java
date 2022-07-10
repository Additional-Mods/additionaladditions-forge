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
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = AdditionalAdditions.namespace, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AdditionalAdditionsClient {
    public static void clientSetup(final FMLClientSetupEvent event) {
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
                if (living == null) return 0.3125F;
                Level world = living.level;

                float sea = world.getSeaLevel();
                float height = living.getBlockY();
                float top = world.getMaxBuildHeight();
                float bottom = world.getMinBuildHeight();

                if (height > top) return 0;
                if (height < bottom) return 1;

                if (height >= sea) {
                    double val = (height / (2 * (sea - top))) + 0.25 - ((sea + top) / (4 * (sea - top)));
                    return (float) val;
                } else {
                    double val = (height / (2 * (bottom - sea))) + 0.75 - ((bottom + sea) / (4 * (bottom - sea)));
                    return (float) val;
                }
            });
        });
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((EntityType<? extends ThrowableItemProjectile>) AdditionalRegistry.GLOW_STICK_ENTITY.get(), ThrownItemRenderer::new);
    }
}
