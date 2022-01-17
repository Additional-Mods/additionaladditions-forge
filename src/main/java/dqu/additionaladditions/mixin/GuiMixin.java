package dqu.additionaladditions.mixin;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.AdditionalRegistry;
import dqu.additionaladditions.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TextComponent;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
@Debug(export=true)
public class GuiMixin {
    @Shadow @Final protected Minecraft minecraft;

    @Redirect(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isScoping()Z"))
    private boolean spyglassOverlay(LocalPlayer clientPlayerEntity) {
        return AdditionalAdditions.zoom || clientPlayerEntity.isScoping();
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V", at = @At("TAIL"))
    private void depthMeterMessage(CallbackInfo ci) {
        if (!Config.get("DepthMeter")) return;
        if (minecraft.player.isHolding(AdditionalRegistry.DEPTH_METER_ITEM.get())) {
            String level = String.valueOf((int)minecraft.player.getY());
            minecraft.player.displayClientMessage(new TextComponent(level), true);
        }
    }
}
