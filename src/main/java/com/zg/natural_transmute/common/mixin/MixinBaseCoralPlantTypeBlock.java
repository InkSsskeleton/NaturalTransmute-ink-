package com.zg.natural_transmute.common.mixin;

import com.zg.natural_transmute.common.items.WaterWax;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseCoralPlantTypeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseCoralPlantTypeBlock.class)
public class MixinBaseCoralPlantTypeBlock {

    @Inject(method = "scanForWater", at = @At(value = "HEAD"), cancellable = true)
    private static void scanForWater(BlockState state, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (state.hasProperty(WaterWax.NO_DIE) && state.getValue(WaterWax.NO_DIE)) {
            cir.setReturnValue(true);
        }
    }

}