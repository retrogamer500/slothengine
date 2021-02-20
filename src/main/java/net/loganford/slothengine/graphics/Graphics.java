package net.loganford.slothengine.graphics;

import lombok.Getter;
import net.loganford.slothengine.Game;
import net.loganford.slothengine.config.json.ImageConfig;

public abstract class Graphics {

    @Getter private Game game;

    public Graphics(Game game) {
        this.game = game;
    }
    public abstract void initialize();
    public abstract boolean closeRequested();

    public abstract void setTitle(String title);
    public abstract void setVsync(boolean vsync);
    public abstract boolean isVsync();

    public abstract Image loadImage(ImageConfig imageConfig);
}
