package net.loganford.slothengine.graphics;

import lombok.Getter;
import lombok.Setter;
import net.loganford.slothengine.Game;
import net.loganford.slothengine.config.json.ImageConfig;

public abstract class Graphics {

    @Getter private Game game;
    @Getter @Setter boolean renderingStatesOffscreen = true;

    public Graphics(Game game) {
        this.game = game;
    }
    public abstract void initialize();
    public abstract boolean closeRequested();

    public abstract void setTitle(String title);
    public abstract void setVsync(boolean vsync);
    public abstract boolean isVsync();

    public abstract Image loadImage(ImageConfig imageConfig);
    public abstract Canvas createCanvas(int width, int height);

    public abstract Canvas getScreenCanvas();
    public abstract void clear();
}
