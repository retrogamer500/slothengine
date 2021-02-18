package net.loganford.slothengine.graphics;


import lombok.Getter;
import lombok.Setter;
import net.loganford.slothengine.resources.PrototypeResource;

public abstract class Image extends PrototypeResource {
    @Getter @Setter private float angle;
    @Getter @Setter private float scaleX = 1;
    @Getter @Setter private float scaleY = 1;

    public abstract void render(Graphics graphics, float x, float y);

    public abstract float getWidth();
    public abstract float getHeight();
}
