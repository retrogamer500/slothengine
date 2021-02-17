package net.loganford.slothFx;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import net.loganford.slothengine.Game;
import net.loganford.slothengine.config.json.ImageConfig;
import net.loganford.slothengine.graphics.Graphics;
import net.loganford.slothengine.graphics.Image;

public class FxGraphics extends Graphics {


    @Getter private Stage stage;
    @Getter private Scene scene;
    @Getter private Canvas canvas;
    @Getter private GraphicsContext graphicsContext;

    @Setter private boolean closeRequested = false;

    public FxGraphics(Game game, Stage stage, Scene scene, Canvas canvas, GraphicsContext graphicsContext) {
        super(game);

        this.stage = stage;
        this.scene = scene;
        this.canvas = canvas;
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
    public Image loadImage(ImageConfig imageConfig) {
        System.out.println("Loading image here!");
        //Todo: implement
        return new Image() {

        };
    }
}

