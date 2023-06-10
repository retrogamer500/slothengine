package net.loganford.slothFx;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.loganford.slothengine.graphics.Font;
import net.loganford.slothengine.graphics.Graphics;

import java.io.InputStream;

public class FxFont extends Font {
    private javafx.scene.text.Font fxFont;
    private Text t = new Text();

    public FxFont(InputStream inputStream, float size) {
        fxFont = javafx.scene.text.Font.loadFont(inputStream, size);
    }

    @Override
    public void render(Graphics graphics, float x, float y, float scale, String text) {
        GraphicsContext gc = ((FxGraphics) graphics).getGraphicsContext();

        gc.save();

        gc.translate(x, y);
        gc.scale(scale, scale);
        gc.setGlobalAlpha(graphics.getAlpha());
        gc.setTextBaseline(VPos.TOP);

        gc.setFont(fxFont);
        gc.setFill(Color.color(graphics.getColor().x, graphics.getColor().y, graphics.getColor().z, graphics.getColor().w));
        gc.fillText(text, 0, 0);

        gc.restore();
    }

    @Override
    public float getWidth(String text) {
        t.setText(text);
        return (float) t.getBoundsInLocal().getWidth();
    }

    @Override
    public float getHeight(String text) {
        t.setText(text);
        return (float) t.getBoundsInLocal().getHeight();
    }
}
