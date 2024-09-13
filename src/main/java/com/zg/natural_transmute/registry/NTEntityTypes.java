package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.entities.animal.LavaAxolotl;
import com.zg.natural_transmute.common.entities.projectile.BreezeArrowEntity;
import com.zg.natural_transmute.common.entities.projectile.RefrigeratedRocketEntity;
import com.zg.natural_transmute.common.entities.projectile.SilverfishPupaEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NTEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, NaturalTransmute.MOD_ID);
    // Projectile
    public static final DeferredHolder<EntityType<?>, EntityType<BreezeArrowEntity>> BREEZE_ARROW = ENTITY_TYPES.register("breeze_arrow",
            () -> EntityType.Builder.<BreezeArrowEntity>of(BreezeArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .eyeHeight(0.13F).clientTrackingRange((4)).updateInterval((20)).build("breeze_arrow"));
    public static final DeferredHolder<EntityType<?>, EntityType<SilverfishPupaEntity>> SILVERFISH_PUPA = ENTITY_TYPES.register("silverfish_pupa",
            () -> EntityType.Builder.<SilverfishPupaEntity>of(SilverfishPupaEntity::new, MobCategory.MISC).sized(0.25F, 0.25F)
                    .clientTrackingRange((4)).updateInterval((10)).noSummon().build("silverfish_pupa"));
    public static final DeferredHolder<EntityType<?>, EntityType<RefrigeratedRocketEntity>> REFRIGERATED_ROCKET = ENTITY_TYPES.register("refrigerated_rocket",
            () -> EntityType.Builder.<RefrigeratedRocketEntity>of(RefrigeratedRocketEntity::new, MobCategory.MISC).sized(0.25F, 0.25F)
                    .clientTrackingRange((4)).updateInterval((10)).fireImmune().build("refrigerated_rocket"));
    // Animal
    public static final DeferredHolder<EntityType<?>, EntityType<LavaAxolotl>> LAVA_AXOLOTL = ENTITY_TYPES.register("lava_axolotl",
            () -> EntityType.Builder.of(LavaAxolotl::new, MobCategory.AXOLOTLS).sized(0.75F, 0.42F)
                    .eyeHeight(0.2751F).clientTrackingRange((10)).fireImmune().build("lava_axolotl"));

}