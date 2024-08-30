package com.zg.natural_transmute.common.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.zg.natural_transmute.common.items.WaterWax;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class MixinBlock {

    @Shadow private BlockState defaultBlockState;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;createBlockStateDefinition(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V"))
    public void init(BlockBehaviour.Properties properties, CallbackInfo ci, @Local StateDefinition.Builder<Block, BlockState> builder) {
        BlockState state = this.defaultBlockState;
        if (state != null && state.getBlock() instanceof CoralBlock) {
            builder.add(WaterWax.NO_DIE);
        }
    }

}