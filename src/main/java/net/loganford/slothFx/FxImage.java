package net.loganford.slothFx;

import javafx.scene.canvas.GraphicsContext;
import lombok.AccessLevel;
import lombok.Setter;
import net.loganford.slothengine.graphics.Graphics;
import net.loganford.slothengine.graphics.Image;

import java.io.InputStream;

public class FxImage extends Image {
    private javafx.scene.image.Image backendImage;

    public FxImage(InputStream inputStream) {
        backendImage = new javafx.scene.image.Image(inputStream);
    }

    protected FxImage(javafx.scene.image.Image backendImage) {
        this.backendImage = backendImage;
    }

    @Override
    public void render(Graphics graphics, float x, float y) {
        GraphicsContext gc = ((FxGraphics) graphics).getGraphicsContext();

        gc.save();


        gc.translate(x + getWidth()/2, y + getHeight()/2);
        gc.scale(getScaleX(), getScaleY());
        gc.rotate(Math.toDegrees(getAngle()));
        gc.translate(-getWidth()/2, -getHeight()/2);
        gc.setGlobalAlpha(graphics.getAlpha());

        gc.drawImage(backendImage, 0, 0);

        gc.setGlobalAlpha(1f);
        gc.restore();
    }

    @Override
    public float getWidth() {
        return (float) backendImage.getWidth();
    }

    @Override
    public float getHeight() {
        return (float) backendImage.getHeight();
    }

    protected void setBackendImage(javafx.scene.image.Image backendImage) {
        this.backendImage = backendImage;
    }
}
