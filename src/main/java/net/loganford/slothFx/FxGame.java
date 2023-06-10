package net.loganford.slothFx;

import javafx.animation.AnimationTimer;
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
import net.loganford.slothengine.audio.Sound;
import net.loganford.slothengine.config.json.SoundConfig;
import net.loganford.slothengine.state.GameState;
import net.loganford.slothengine.utils.file.DataSource;

public class FxGame extends Game {
    private Stage stage;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Timeline timeline;
    private AnimationTimer animationTimer;

    public FxGame(GameState gameState) {
        super(gameState);
    }

    @Override
    public void initialize() {
        FxGraphics fxGraphics = new FxGraphics(this, stage, scene, canvas, timeline, animationTimer, graphicsContext);
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
    public Sound loadSound(SoundConfig soundConfig) {
        DataSource location = soundConfig.getResourceMapper().get((soundConfig.getFilename()));
        return new FxSound(location.getInputStream());
    }

    @Override
    public void stopAllSounds() {
        FxSoundSystem.getInstance().getClips().forEach(c -> {
            c.stop();
            c.close();
        });
        FxSoundSystem.getInstance().getClips().clear();
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

            //Timeline for unlimited frames
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            actionEvent -> {
                                graphicsContext.clearRect(0, 0, 640, 480);
                                game.onFxTick();
                            }
                    )
            );

            //Animation timer for use when vsync is enabled
            AnimationTimer animationTimer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    graphicsContext.clearRect(0, 0, 640, 480);
                    game.onFxTick();
                }
            };
            timeline.setRate( ((double) Game.NANOSECONDS_IN_SECOND) / game.getMaxFrameTimeNs());
            timeline.setCycleCount(Timeline.INDEFINITE);

            game.timeline = timeline;
            game.animationTimer = animationTimer;

            game.initialize();

            //Call setVsync to kick off either animation timer or timeline based on the vsync setting
            game.getGraphics().setVsync(game.getGraphics().isVsync());
        }
    }
}
