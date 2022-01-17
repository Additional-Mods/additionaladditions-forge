package dqu.additionaladditions.mixin;

import dqu.additionaladditions.AdditionalRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RedStoneWireBlock.class)
public class RedstoneWireBlockMixin {
    @Redirect(method = "calculateTargetStrength", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBestNeighborSignal(Lnet/minecraft/core/BlockPos;)I"))
    private int getWireSignal(Level world, BlockPos pos) {
        int i = 0;
        Direction[] var3 = Direction.values();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Direction direction = var3[var5];
            BlockPos offsetted = pos.offset(direction.getNormal());
            BlockState state = world.getBlockState(offsetted);
            int j = 0;
            if (!state.is(AdditionalRegistry.COPPER_PATINA.get())) {
                j = world.getSignal(offsetted, direction);
            }
            if (j >= 15) {
                return 15;
            }
            if (j > i) {
                i = j;
            }
        }

        return i;
    }
}
