package net.loganford.slothengine;

import lombok.Getter;
import lombok.Setter;

public class Window {
    private Game game;

    @Getter private int width = 640, height = 480;
    @Getter private boolean isResizable = true;

    public Window(Game game) {
        this.game = game;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        game.onResize();
    }

    public void setResizable(boolean isResizable) {
        this.isResizable = isResizable;
        if(isResizable) {
            game.onResizeEnable();
        }
        else {
            game.onResizeDisable();
        }
    }

    public void setWidthInternal(int width) {
        this.width = width;
    }

    public void setHeightInternal(int height) {
        this.height = height;
    }
}
