package com.zg.natural_transmute.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SimulatedRambler extends GlowLichenBlock {

    public SimulatedRambler() {
        super(Properties.ofFullCopy(Blocks.GLOW_LICHEN).lightLevel(GlowLichenBlock.emission(9)));
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!level.isClientSide && entity instanceof LivingEntity livingEntity) {
            if (livingEntity.tickCount % 100 == 0) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 600));
            }
        }
    }

}