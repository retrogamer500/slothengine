package net.loganford.slothFx;

import lombok.Getter;

import javax.sound.sampled.Clip;
import java.util.HashSet;
import java.util.Set;

public class FxSoundSystem {
    private static FxSoundSystem instance;
    @Getter private Set<Clip> clips = new HashSet<>();

    public static FxSoundSystem getInstance() {
        if(instance == null) {
            instance = new FxSoundSystem();
        }

        return instance;
    }
}
