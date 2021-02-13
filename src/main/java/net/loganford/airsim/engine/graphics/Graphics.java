package net.loganford.airsim.engine.graphics;

import net.loganford.airsim.engine.Input;

public abstract class Graphics {
    public abstract void initialize();
    public abstract boolean closeRequested();
    public abstract Input getDefaultInput();
}
