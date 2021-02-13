package net.loganford.airsim.engine;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.loganford.airsim.engine.graphics.Graphics;
import net.loganford.airsim.javaGraphics.JavaGraphics;

@Log4j2
public class Game {
    public static final long NANOSECONDS_IN_SECOND = 1000000000;
    public static final long NANOSECONDS_IN_MILLISECOND = 1000000;
    public static final long MILLISECONDS_IN_SECOND = 1000;
    public static final long SLEEP_BUFFER_MS = 2;

    private boolean running = true;

    @Getter private int maxFps;
    @Getter private int minFps;
    private long lastFrameTime;
    private long maxFrameTimeNs = NANOSECONDS_IN_SECOND / 144L;
    private long minFrameTimeNs = NANOSECONDS_IN_SECOND / 60L;

    @Getter @Setter public Graphics graphics = new JavaGraphics();
    @Getter @Setter public Input input;

    @Getter private GameState gameState;

    public Game(GameState gameState) {
        this.gameState = gameState;
    }

    protected void startGame() {
        graphics.initialize();
        input = graphics.getDefaultInput();
        input.initialize();

        log.info("Setting up game states");
        gameState.beginState(this);
    }

    public void run() {
        startGame();

        log.info("Entering game loop");
        lastFrameTime = System.nanoTime();

        while(running) {
            long currentTime = System.nanoTime();
            long deltaTimeNs = currentTime - lastFrameTime;

            long sleepTimeMs = ((maxFrameTimeNs - deltaTimeNs)/ NANOSECONDS_IN_MILLISECOND) - SLEEP_BUFFER_MS;
            if(sleepTimeMs > 0) {
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

                //Update input
                //input.stepInput(window);
                lastFrameTime = currentTime;

                stepAndRender((float)deltaTimeNs / NANOSECONDS_IN_MILLISECOND);

                //Handle input
                input.processInput();
            }

            //Exit the game if requested by the window
            if (graphics.closeRequested()) {
                endGame();
            }
        }
    }

    public void endGame() {
        log.info("Ending game");
        running = false;
    }

    public void setFps(int min, int max) {
        this.minFps = min;
        this.maxFps = max;

        maxFrameTimeNs = NANOSECONDS_IN_SECOND / (long)max;
        minFrameTimeNs = NANOSECONDS_IN_SECOND / (long)min;
    }

    protected void stepAndRender(float delta) {
        if(input.keyPressed(Input.KEY_SPACE)) {
            log.info("Space is pressed!");
        }

        gameState.renderState(this, graphics);
        gameState.stepState(this, delta);
    }
}
