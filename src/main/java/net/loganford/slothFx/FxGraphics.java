package net.loganford.slothFx;

import com.sun.javafx.geom.Rectangle;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.loganford.slothengine.Game;
import net.loganford.slothengine.config.json.ImageConfig;
import net.loganford.slothengine.graphics.Canvas;
import net.loganford.slothengine.graphics.Graphics;
import net.loganford.slothengine.graphics.Image;
import net.loganford.slothengine.utils.file.DataSource;

public class FxGraphics extends Graphics {

    @Getter private Stage stage;
    @Getter private Scene scene;
    private Canvas screenCanvas;
    @Getter private Timeline timeline;
    @Getter private AnimationTimer animationTimer;
    @Getter @Setter(AccessLevel.PROTECTED) private GraphicsContext graphicsContext;

    @Setter private boolean closeRequested = false;
    private boolean vsync = false;

    private static Rectangle RECT = new Rectangle(0, 0, 0, 0);

    public FxGraphics(Game game, Stage stage, Scene scene, javafx.scene.canvas.Canvas internalCanvas, Timeline timeline, AnimationTimer animationTimer, GraphicsContext graphicsContext) {
        super(game);

        this.stage = stage;
        this.scene = scene;
        this.screenCanvas = new FxCanvas(this, internalCanvas);
        this.timeline = timeline;
        this.animationTimer = animationTimer;
        this.graphicsContext = graphicsContext;
    }

    @Override
    public void initialize() {

    }

    @Override
    public boolean closeRequested() {
        return closeRequested;
    }

    @Override
    public void setTitle(String title) {
        stage.setTitle(title);
    }

    @Override
    public void setVsync(boolean vsync) {
        this.vsync = vsync;
        if(vsync) {
            timeline.stop();
            animationTimer.start();
        }
        else {
            animationTimer.stop();
            timeline.play();
        }
    }

    @Override
    public boolean isVsync() {
        return vsync;
    }

    @Override
    public Image loadImage(ImageConfig imageConfig) {
        DataSource location = imageConfig.getResourceMapper().get((imageConfig.getFilename()));
        return new FxImage(location.getInputStream());
    }

    @Override
    public net.loganford.slothengine.graphics.Canvas createCanvas(int width, int height) {
        return new FxCanvas(this, width, height);
    }

    @Override
    public Canvas getScreenCanvas() {
        return screenCanvas;
    }

    @Override
    public void clear() {
        getGraphicsContext().clearRect(0, 0, 640, 480); //Todo: move to canvas?
    }

    @Override
    public void drawRectangle(float x, float y, float width, float height) {
        graphicsContext.setFill(Color.color(getColor().x, getColor().y, getColor().z, getColor().w));
        graphicsContext.fillRect(x, y, width, height);
    }

    @Override
    public void drawRectangleOutline(float x, float y, float width, float height, float outlineWidth) {
        graphicsContext.setFill(Color.color(getColor().x, getColor().y, getColor().z, getColor().w));
        graphicsContext.setLineWidth(width);
        graphicsContext.strokeRect(x, y, width, height);
    }

    @Override
    public void drawEllipse(float x, float y, float width, float height) {
        graphicsContext.setFill(Color.color(getColor().x, getColor().y, getColor().z, getColor().w));
        graphicsContext.fillOval(x, y, width, height);
    }

    @Override
    public void drawEllipseOutline(float x, float y, float width, float height, float outlineWidth) {
        graphicsContext.setFill(Color.color(getColor().x, getColor().y, getColor().z, getColor().w));
        graphicsContext.setLineWidth(width);
        graphicsContext.strokeOval(x, y, width, height);
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2, float width) {
        graphicsContext.setFill(Color.color(getColor().x, getColor().y, getColor().z, getColor().w));
        graphicsContext.setLineWidth(width);
        graphicsContext.strokeLine(x1, y1, x2, y2);
    }

}

