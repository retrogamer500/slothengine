package net.loganford.slothengine.graphics;

public abstract class Canvas {
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract void size(int width, int height);

    protected abstract void use();

    public abstract Image getImage();

    public void cleanup() {};
}
