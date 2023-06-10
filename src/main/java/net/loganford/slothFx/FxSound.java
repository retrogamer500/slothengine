package net.loganford.slothFx;

import lombok.extern.log4j.Log4j2;
import net.loganford.slothengine.audio.Playback;
import net.loganford.slothengine.audio.Sound;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Log4j2
public class FxSound extends Sound {
    private byte[] data;
    private long soundInvocation = Long.MIN_VALUE;
    private Clip clip;

    public FxSound(InputStream inputStream) {
        try {
            data = inputStream.readAllBytes();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Playback play(float volume) {
        return play(false, volume);
    }

    @Override
    public Playback loop(float volume) {
        return play(true, volume);
    }


    private Playback play(boolean loop, float volume) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(data)));

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(volume));

            clip.loop(loop ? Clip.LOOP_CONTINUOUSLY : 0);

            clip.addLineListener((l) -> {
                if(l.getType() == LineEvent.Type.STOP) {
                    Clip lineClip = (Clip) l.getLine();
                    FxSoundSystem.getInstance().getClips().remove(lineClip);
                    lineClip.close();
                }
            });
            FxSoundSystem.getInstance().getClips().add(clip);
            return new FxPlayback(clip);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            log.warn("Unable to play sound!", e);
        }
        return new FxPlayback(null);
    }
}
