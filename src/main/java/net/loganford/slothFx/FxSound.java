package net.loganford.slothFx;

import lombok.Getter;
import net.loganford.slothengine.GameEngineException;
import net.loganford.slothengine.audio.Sound;
import net.loganford.slothengine.resources.PrototypeResource;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class FxSound extends Sound {
    @Getter private AudioInputStream inputStream;
    private long soundInvocation = Long.MIN_VALUE;

    public FxSound(InputStream inputStream) {
        try {
            this.inputStream = AudioSystem.getAudioInputStream(inputStream);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new GameEngineException(e.getMessage());
        }
    }

    @Override
    public void play() {
        soundInvocation = FxAudioSystem.getInstance().play(this, false);
    }

    @Override
    public void loop() {
        soundInvocation = FxAudioSystem.getInstance().play(this, false);
    }

    @Override
    public void stop() {
        FxAudioSystem.getInstance().stop(soundInvocation);
    }

    @Override
    public PrototypeResource clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public AudioFormat getFormat() {
        return inputStream.getFormat();
    }
}
