package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

public class NTJukeboxSongs {

    public static final ResourceKey<JukeboxSong> EMPTY = createKey("empty");

    private static ResourceKey<JukeboxSong> createKey(String key) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, NaturalTransmute.prefix(key));
    }

}