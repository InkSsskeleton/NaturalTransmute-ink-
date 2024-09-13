package com.zg.natural_transmute.common.data.advancements.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zg.natural_transmute.registry.NTTriggerTypes;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class HarmoniousChangeTrigger extends SimpleCriterionTrigger<HarmoniousChangeTrigger.TriggerInstance> {

    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack item) {
        this.trigger(player, instance -> instance.matches(item));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                ItemPredicate.CODEC.optionalFieldOf("item").forGetter(TriggerInstance::item)
        ).apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> harmoniousChange() {
            return NTTriggerTypes.HARMONIOUS_CHANGE.get().createCriterion(new TriggerInstance(Optional.empty(), Optional.empty()));
        }

        public boolean matches(ItemStack item) {
            return this.item.isPresent() && this.item.get().test(item);
        }

    }

}