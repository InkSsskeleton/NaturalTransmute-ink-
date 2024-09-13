package com.zg.natural_transmute.common.mixin;

import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.item.JukeboxSong;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(JukeboxSong.class)
public class MixinJukeboxSong {

    @Inject(method = "fromStack", at = @At(value = "HEAD"), cancellable = true)
    private static void fromStack(HolderLookup.Provider registries, ItemStack stack, CallbackInfoReturnable<Optional<Holder<JukeboxSong>>> cir) {
        if (stack.is(NTItems.MELODIOUS_DISC)) {
            List<ResourceKey<JukeboxSong>> list = new ArrayList<>();
            registries.lookupOrThrow(Registries.JUKEBOX_SONG).listElementIds().forEach(list::add);
            ResourceKey<JukeboxSong> key = list.get(RandomSource.create().nextInt(list.size()));
            JukeboxPlayable jukeboxPlayable = new JukeboxPlayable(new EitherHolder<>(key), Boolean.TRUE);
            cir.setReturnValue(jukeboxPlayable.song().unwrap(registries));
        }
    }

}