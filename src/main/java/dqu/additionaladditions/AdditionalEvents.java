package dqu.additionaladditions;

import dqu.additionaladditions.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AdditionalAdditions.namespace, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class AdditionalEvents {
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
