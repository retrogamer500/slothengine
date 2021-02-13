package net.loganford.airsim.javaGraphics;

import net.loganford.airsim.engine.Input;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JavaInput extends Input {
    public static short UP = 0;
    public static short PRESSED = 1;
    public static short DOWN = 2;
    public static short RELEASED = 3;

    private short[] currentBuffer = new short[2048];;
    private short[] nextBuffer = new short[2048];;


    private JFrame frame;

    public JavaInput(JFrame frame) {
        this.frame = frame;
    }

    public void initialize() {
        frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() < MAX_KEYCODE && e.getKeyCode() >= 0) {
                    nextBuffer[e.getKeyCode()] = PRESSED;
                }
            }

            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() < MAX_KEYCODE && e.getKeyCode() >= 0) {
                    nextBuffer[e.getKeyCode()] = RELEASED;
                }
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

    public boolean keyUp(int key) {
        return currentBuffer[key] == RELEASED;
    }
}
