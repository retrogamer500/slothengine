package net.loganford.slothengine.audio;

import lombok.Getter;
import lombok.Setter;
import net.loganford.slothengine.resources.PrototypeResource;

public abstract class Sound extends PrototypeResource {
    @Getter @Setter public float volume = 1f;
    public abstract Playback play(float volume);
    public abstract Playback loop(float volume);

    public Playback play() {
        return play(1f);
    }

    public Playback loop() {
        return loop(1f);
    }
}
