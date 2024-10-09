package com.zg.natural_transmute.common.entities.projectile;

import com.zg.natural_transmute.common.entities.animal.Duck;
import com.zg.natural_transmute.registry.NTEntityTypes;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ThrownDuckEggEntity extends ThrownEgg {

    private static final EntityDimensions ZERO_SIZED_DIMENSIONS = EntityDimensions.fixed(0.0F, 0.0F);

    public ThrownDuckEggEntity(EntityType<? extends ThrownEgg> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownDuckEggEntity(Level level, LivingEntity shooter) {
        super(level, shooter);
    }

    public ThrownDuckEggEntity(Level level, double x, double y, double z) {
        super(level, x, y, z);
    }

    @Override
    public EntityType<?> getType() {
        return NTEntityTypes.DUCK_EGG.get();
    }

    @Override
    protected void onHit(HitResult result) {
        if (!this.level().isClientSide) {
            if (this.random.nextInt(8) == 0) {
                int i = 1;
                if (this.random.nextInt(32) == 0) {
                    i = 4;
                }

                for (int j = 0; j < i; j++) {
                    Duck duck = NTEntityTypes.DUCK.get().create(this.level());
                    if (duck != null) {
                        duck.setAge(-24000);
                        duck.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                        if (!duck.fudgePositionAfterSizeChange(ZERO_SIZED_DIMENSIONS)) {
                            break;
                        }

                        this.level().addFreshEntity(duck);
                    }
                }
            }

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return NTItems.DUCK.get();
    }

}