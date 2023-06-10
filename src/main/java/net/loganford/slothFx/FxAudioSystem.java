package net.loganford.slothFx;

import lombok.extern.log4j.Log4j2;
import net.loganford.slothengine.GameEngineException;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log4j2
public class FxAudioSystem {
    private static FxAudioSystem singleton;

    private static long ERROR = Long.MIN_VALUE;
    private long previousSoundInvocation = Long.MIN_VALUE;

    private List<Clip> availableLines;
    private HashMap<Long, Clip> activeLines;
    private int totalLines = 0;

    public static FxAudioSystem getInstance() {
        if(singleton == null) {
            singleton = new FxAudioSystem();
        }
        return singleton;
    }

    public FxAudioSystem() {
        availableLines = new ArrayList<>();
        activeLines = new HashMap<>();
    }

    public long play(FxSound fxSound, boolean loop) {
        Clip availableLine = null;

        //Obtain a line
        try {
            for (Clip potentialLine : availableLines) {
                if (fxSound.getFormat().matches(potentialLine.getFormat())) {
                    availableLine = potentialLine;
                    break;
                }
            }
            if (availableLine == null) {
                //No available line exists, so create one
                DataLine.Info info = new DataLine.Info(Clip.class, fxSound.getFormat());
                availableLine = (Clip) AudioSystem.getLine(info);
                totalLines++;
                log.info("Creating new audio line. " + totalLines + " lines total created.");
            }
            else {
                availableLines.remove(availableLine);
            }
        }
        catch (LineUnavailableException e) {
            log.warn("Line is unavailable");
            return ERROR;
        }

        //Play audio
        try {
            availableLine.open(fxSound.getInputStream());
            availableLine.setFramePosition(0);
            availableLine.start();

            availableLine.addLineListener((e) -> {
                if ( e.getType() == LineEvent.Type.STOP) {
                    ((Clip)e.getLine()).setFramePosition(0);
                    Clip activeLine = (Clip) e.getLine();
                    activeLine.stop();
                    activeLine.close();
                    activeLines.entrySet().removeIf((v) -> v.getValue().equals(activeLine));
                    availableLines.add(activeLine);
                }
            });
        }
        catch(IOException | LineUnavailableException e) {
            log.warn("Error playing on a line.");
            availableLine.close();
        }

        previousSoundInvocation++;
        activeLines.put(previousSoundInvocation, availableLine);
        return previousSoundInvocation;
    }

    public void stop(long soundInvocation) {
        Clip activeLine = activeLines.remove(soundInvocation);
        if(activeLine != null) {
            activeLine.stop();
            activeLine.close();
            availableLines.add(activeLine);
        }
    }

    public void stopAll() {

    }
}
