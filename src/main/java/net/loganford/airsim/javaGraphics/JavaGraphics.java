package net.loganford.airsim.javaGraphics;

import lombok.Getter;
import net.loganford.airsim.engine.Input;
import net.loganford.airsim.engine.graphics.Graphics;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JavaGraphics extends Graphics {

    @Getter private JFrame frame;
    private boolean closeRequested = false;
    private JavaInput input;

    public void initialize() {
        frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                closeRequested = true;
            }
        });
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setVisible(true);
    }

    public boolean closeRequested() {
        return closeRequested;
    }

    public Input getDefaultInput() {
        if(input == null) {
            input = new JavaInput(frame);
        }
        return input;
    }
}
