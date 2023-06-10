package net.loganford.slothengine;

import lombok.extern.log4j.Log4j2;
import net.loganford.slothengine.state.GameState;

@Log4j2
public abstract class BasicGame extends Game {

    public BasicGame(GameState gameState) {
        super(gameState);
    }

    @Override
    public void run() {
        initialize();

        while (isRunning()) {
            long currentTime = System.nanoTime();
            long deltaTimeNs = currentTime - lastFrameTime;

            long sleepTimeMs = ((maxFrameTimeNs - deltaTimeNs) / NANOSECONDS_IN_MILLISECOND) - SLEEP_BUFFER_MS;
            if (sleepTimeMs > 0) {
                try {
                    Thread.sleep(sleepTimeMs);
                } catch (InterruptedException e) {
                    log.error(e);
                }
            }

            if (deltaTimeNs > maxFrameTimeNs) {
                if (deltaTimeNs > minFrameTimeNs) {
                    //Alert low FPS if FPS falls below 3 percent of target
                    if (deltaTimeNs > minFrameTimeNs * 1.03) {
                        log.warn("Low FPS!");
                    }
                    deltaTimeNs = minFrameTimeNs;
                }

                lastFrameTime = currentTime;

                stepAndRender((float) deltaTimeNs / NANOSECONDS_IN_MILLISECOND);

            }

            //Exit the game if requested by the window
            if (getGraphics().closeRequested()) {
                endGame();
            }
        }
    }
}
