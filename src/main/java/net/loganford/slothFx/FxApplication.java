package net.loganford.slothFx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.log4j.Log4j2;
import net.loganford.slothengine.Game;
import net.loganford.slothengine.state.GameState;



@Log4j2
public class FxApplication extends Application {
    private Stage stage;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Timeline timeline;

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Canvas canvas = new Canvas(640, 480);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        this.stage = stage;
        this.scene = scene;
        this.canvas = canvas;
        this.graphicsContext = graphicsContext;

        FxGame fxGame = new FxGame(new GameState());

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        actionEvent -> {
                            long currentTime = System.nanoTime();
                            fxGame.run(currentTime);
                        }
                )
        );

        timeline.setRate( ((double) Game.NANOSECONDS_IN_SECOND) / fxGame.getMaxFrameTimeNs() * 0.99);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void setRate() {
        //timeline.setRate(10);
    }

    public class FxGame extends Game {

        public FxGame(GameState gameState) {
            super(gameState);

            FxGraphics fxGraphics = new FxGraphics(this, stage, scene, canvas, graphicsContext);
            setGraphics(fxGraphics);
            setInput(new FxInput(fxGraphics));

            initialize();
        }

        public void run(long currentTime) {
            long deltaTimeNs = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            //Spin loop until we can progress
            while(deltaTimeNs < maxFrameTimeNs) {
                deltaTimeNs = System.nanoTime() - lastFrameTime;
            }

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

        @Override
        public void setFps(int min, int max) {
            super.setFps(min, max);
            if(timeline != null) {
                timeline.setRate(((double) Game.NANOSECONDS_IN_SECOND) / this.getMaxFrameTimeNs() * 0.99);
            }
        }
    }
}
