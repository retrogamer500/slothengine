package net.loganford.slothFx;

import javafx.scene.Scene;
import net.loganford.slothengine.Input;

public class FxInput extends Input {
    public static short UP = 0;
    public static short PRESSED = 1;
    public static short DOWN = 2;
    public static short RELEASED = 3;

    private short[] currentBuffer = new short[2048];;
    private short[] nextBuffer = new short[2048];;

    private FxGraphics fxGraphics;
    private Scene scene;

    public FxInput(FxGraphics fxGraphics) {
        this.fxGraphics = fxGraphics;
    }

    public void initialize() {
        this.scene = fxGraphics.getScene();

        scene.setOnKeyPressed(e -> {
            if (e.getCode().getCode() < MAX_KEYCODE && e.getCode().getCode() >= 0) {
                nextBuffer[e.getCode().getCode()] = PRESSED;
            }
        });

        scene.setOnKeyReleased(e -> {
            if(e.getCode().getCode() < MAX_KEYCODE && e.getCode().getCode() >= 0) {
                nextBuffer[e.getCode().getCode()] = RELEASED;
            }
        });
    }

    public void processInput() {
        for(short i = 0; i < MAX_KEYCODE; i++) {
            if(nextBuffer[i] == PRESSED) {
                if(currentBuffer[i] != DOWN) {
                    currentBuffer[i] = PRESSED;
                }
            }
            else if(nextBuffer[i] == RELEASED) {
                currentBuffer[i] = RELEASED;
            }
            else {
                if(currentBuffer[i] == PRESSED) {
                    currentBuffer[i] = DOWN;
                }
                if(currentBuffer[i] == RELEASED) {
                    currentBuffer[i] = UP;
                }
            }
            nextBuffer[i] = UP;
        }
    }

    public boolean keyDown(int key) {
        return currentBuffer[key] == DOWN || currentBuffer[key] == PRESSED;
    }

    public boolean keyPressed(int key) {
        return currentBuffer[key] == PRESSED;
    }

    public boolean keyReleased(int key) {
        return currentBuffer[key] == RELEASED;
    }
}
