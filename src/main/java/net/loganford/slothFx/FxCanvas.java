package net.loganford.slothFx;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import net.loganford.slothengine.graphics.Canvas;
import net.loganford.slothengine.graphics.Image;

public class FxCanvas extends Canvas {
    private FxGraphics fxGraphics;
    private javafx.scene.canvas.Canvas internalCanvas;
    private WritableImage writableImage;
    private SnapshotParameters snapshotParams;
    private FxImage fxImage;

    private int width;
    private int height;

    public FxCanvas(FxGraphics fxGraphics, int width, int height) {
        this.fxGraphics = fxGraphics;
        this.width = width;
        this.height = height;
        internalCanvas = new javafx.scene.canvas.Canvas(width, height);
        writableImage = new WritableImage(width, height);

        snapshotParams = new SnapshotParameters();
        snapshotParams.setFill(Color.TRANSPARENT);
        fxImage = new FxImage(writableImage);
    }

    public FxCanvas(FxGraphics fxGraphics, javafx.scene.canvas.Canvas internalCanvas) {
        this.fxGraphics = fxGraphics;
        this.width = (int) internalCanvas.getWidth();
        this.height = (int) internalCanvas.getHeight();
        this.internalCanvas = internalCanvas;

        snapshotParams = new SnapshotParameters();
        snapshotParams.setFill(Color.TRANSPARENT);
        fxImage = new FxImage(writableImage);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void size(int width, int height) {
        this.width = width;
        this.height = height;
        internalCanvas.setWidth(width);
        internalCanvas.setHeight(height);
        writableImage = new WritableImage(width, height);
    }

    @Override
    public void use() {
        fxGraphics.setGraphicsContext(internalCanvas.getGraphicsContext2D());
    }

    public Image getImage() {
        WritableImage image = internalCanvas.snapshot(snapshotParams, writableImage);
        fxImage.setBackendImage(image);
        return fxImage;
    }
}
