package net.loganford.slothengine.graphics;

import lombok.Getter;
import lombok.Setter;
import net.loganford.slothengine.Game;
import net.loganford.slothengine.config.json.FontConfig;
import net.loganford.slothengine.config.json.ImageConfig;
import org.joml.Vector4f;

import java.util.Stack;

public abstract class Graphics {

    @Getter private Game game;
    @Getter @Setter private Vector4f color = new Vector4f(0f, 0f, 0f, 1f);
    private Stack<Canvas> canvasStack = new Stack<>();

    public Graphics(Game game) {
        this.game = game;
    }
    public abstract void initialize();
    public abstract boolean closeRequested();

    public abstract void setTitle(String title);
    public abstract void setVsync(boolean vsync);
    public abstract boolean isVsync();

    public abstract Image loadImage(ImageConfig imageConfig);
    public abstract Font loadFont(FontConfig fontConfig);
    public abstract Canvas createCanvas(int width, int height);

    public void useCanvas(Canvas canvas) {
        canvasStack.push(canvas);
        canvas.use();
    }
    public void popCanvas() {
        canvasStack.pop();
        canvasStack.peek().use();
    }

    public abstract Canvas getScreenCanvas();
    public abstract void clear();

    public void setColor(float r, float g, float b, float a) {
        getColor().set(r, g, b, a);
    }

    public void setColor(float r, float g, float b) {
        setColor(r, g, b, 1f);
    }

    public float getAlpha() {
        return getColor().w;
    }

    public void setAlpha(float alpha) {
        setColor(getColor().x, getColor().y, getColor().z, alpha);
    }

    public abstract void drawRectangle(float x, float y, float width, float height);
    public abstract void drawRectangleOutline(float x, float y, float width, float height, float outlineWidth);

    public abstract void drawEllipse(float x, float y, float width, float height);
    public abstract void drawEllipseOutline(float x, float y, float width, float height, float outlineWidth);

    public void drawCircle(float x, float y, float width) {
        drawEllipse(x, y, width, width);
    }
    public void drawCircleOutline(float x, float y, float width, float height, float outlineWidth) {
        drawEllipseOutline(x, y, width, width, outlineWidth);
    }

    public abstract void drawLine(float x1, float y1, float x2, float y2, float width);
    public void drawLine(float x1, float y1, float x2, float y2) {
        drawLine(x1, y1, x2, y2, 1f);
    }
}
