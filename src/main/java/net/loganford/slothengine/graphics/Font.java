package net.loganford.slothengine.graphics;

import net.loganford.slothengine.resources.PrototypeResource;

public abstract class Font extends PrototypeResource {
    public abstract void render(Graphics graphics, float x, float y, float scale, String text);

    public void render (Graphics graphics, float x, float y, String text) {
        render(graphics, x, y, 1f, text);
    }

    public abstract float getWidth(String text);
    public abstract float getHeight(String text);

}
