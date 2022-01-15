package dqu.additionaladditions;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = AdditionalAdditions.namespace, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AdditionalAdditionsClient {
    public static void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(AdditionalRegistry.COPPER_PATINA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AdditionalRegistry.ROPE_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AdditionalRegistry.GLOW_STICK_BLOCK.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AdditionalRegistry.GLOW_STICK_ENTITY_ENTITY_TYPE, ThrownItemRenderer::new);
    }
}
