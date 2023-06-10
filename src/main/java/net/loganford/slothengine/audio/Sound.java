package net.loganford.slothengine.audio;

import lombok.Getter;
import lombok.Setter;
import net.loganford.slothengine.resources.PrototypeResource;

public abstract class Sound extends PrototypeResource {
    @Getter @Setter public float volume = 1f;
    public abstract Playback play();
    public abstract Playback loop();
}
