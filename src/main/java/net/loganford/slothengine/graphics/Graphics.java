package net.loganford.slothengine.graphics;

import net.loganford.slothengine.Input;
import net.loganford.slothengine.config.json.ImageConfig;

public abstract class Graphics {
    public abstract void initialize();
    public abstract boolean closeRequested();
    public abstract Input getDefaultInput();
    public abstract void setTitle(String title);
    public abstract Image loadImage(ImageConfig imageConfig);
}
