package com.zg.natural_transmute.common.mixin;

import com.zg.natural_transmute.common.items.WaterWax;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseCoralPlantTypeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseCoralPlantTypeBlock.class)
public class MixinBaseCoralPlantTypeBlock extends Block {

    public MixinBaseCoralPlantTypeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WaterWax.NO_DIE, Boolean.FALSE));
    }

    @Inject(method = "tryScheduleDieTick", at = @At(value = "HEAD"), cancellable = true)
    protected void tryScheduleDieTick(BlockState state, LevelAccessor level, BlockPos pos, CallbackInfo ci) {
        if (state.getValue(WaterWax.NO_DIE)) {
            ci.cancel();
        }
    }

    @Inject(method = "createBlockStateDefinition", at = @At(value = "TAIL"))
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(WaterWax.NO_DIE);
    }

}