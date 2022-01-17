package dqu.additionaladditions.mixin;

import dqu.additionaladditions.AdditionalRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(PiglinAi.class)
public class PiglinAiMixin {
    @Inject(method = "isWearingGold", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void wearsGoldArmor(LivingEntity entity, CallbackInfoReturnable<Boolean> cir, Iterator var1, ItemStack itemstack) {
        Item item = itemstack.getItem();
        if (item instanceof ArmorItem && ((ArmorItem) item).getMaterial() == AdditionalRegistry.GILDED_NETHERITE_ARMOR_MATERIAL) {
            cir.setReturnValue(true);
        }
    }
}
