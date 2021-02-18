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
import net.loganford.slothengine.Game;
import net.loganford.slothengine.state.GameState;

public class FxGame extends Game {
    private Stage stage;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Timeline timeline;

    public FxGame(GameState gameState) {
        super(gameState);
    }

    @Override
    public void initialize() {
        FxGraphics fxGraphics = new FxGraphics(this, stage, scene, canvas, graphicsContext);
        setGraphics(fxGraphics);
        setInput(new FxInput(fxGraphics));

        super.initialize();
    }

    @Override
    public void run() {
        FxGame.GAME = this;
        Application.launch(FxApplication.class);
    }

    @Override
    public void setFps(int min, int max) {
        super.setFps(min, max);
        if(timeline != null) {
            timeline.setRate(((double) Game.NANOSECONDS_IN_SECOND) / this.getMaxFrameTimeNs());
        }
    }

    private void onFxTick() {
        long currentTime = System.nanoTime();
        long deltaTimeNs = currentTime - lastFrameTime;

        //Spin loop until we can progress
        while(deltaTimeNs < maxFrameTimeNs) {
            deltaTimeNs = System.nanoTime() - lastFrameTime;
        }

        lastFrameTime = currentTime;

        if (deltaTimeNs > minFrameTimeNs) {
            deltaTimeNs = minFrameTimeNs;
        }

        stepAndRender((float) deltaTimeNs / NANOSECONDS_IN_MILLISECOND);
    }

    public static FxGame GAME;
    public static class FxApplication extends Application {

        private FxGame game;

        @Override
        public void start(Stage stage) throws Exception {
            game = FxGame.GAME;

            Group root = new Group();
            Canvas canvas = new Canvas(640, 480);
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            root.getChildren().add(canvas);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            game.stage = stage;
            game.scene = scene;
            game.canvas = canvas;
            game.graphicsContext = graphicsContext;

            game.initialize();

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            actionEvent -> {
                                graphicsContext.clearRect(0, 0, 640, 480);
                                game.onFxTick();
                            }
                    )
            );

            game.timeline = timeline;

            timeline.setRate( ((double) Game.NANOSECONDS_IN_SECOND) / game.getMaxFrameTimeNs());
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }
}
