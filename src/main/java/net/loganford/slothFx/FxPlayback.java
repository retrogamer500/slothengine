package net.loganford.slothFx;

import net.loganford.slothengine.audio.Playback;

import javax.sound.sampled.Clip;

public class FxPlayback extends Playback {
    private Clip clip;

    public FxPlayback(Clip clip) {

    }
    @Override
    public void stop() {
        if(clip != null && clip.isOpen()) {
            clip.stop();
            clip.close();
            FxSoundSystem.getInstance().getClips().remove(clip);
        }
    }
}
