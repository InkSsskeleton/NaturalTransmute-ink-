package com.zg.natural_transmute.common.mixin;

import com.zg.natural_transmute.common.items.WaterWax;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CoralBlock.class)
public class MixinCoralBlock extends Block {

    public MixinCoralBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WaterWax.NO_DIE, Boolean.FALSE));
    }

    @Inject(method = "scanForWater", at = @At(value = "HEAD"), cancellable = true)
    protected void scanForWater(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (level.getBlockState(pos).getValue(WaterWax.NO_DIE)) {
            cir.setReturnValue(false);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WaterWax.NO_DIE);
    }

}