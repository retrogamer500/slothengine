package net.loganford.slothengine;

import net.loganford.slothFx.FxGame;
import net.loganford.slothengine.graphics.Canvas;
import net.loganford.slothengine.graphics.Graphics;
import net.loganford.slothengine.graphics.Image;
import net.loganford.slothengine.state.GameState;
import org.junit.Test;

public class GameTest {
    @Test
    public void testGame() {
        Game game = new FxGame(new GameState() {

            private Image slothImage;
            private Canvas testCanvas;

            @Override
            public void beginState(Game game) {
                super.beginState(game);
                game.setFps(30, 144);
                game.getGraphics().setVsync(true);
                slothImage = game.getImageManager().get("sloth");
                testCanvas = game.getGraphics().createCanvas(64, 64);
            }

            @Override
            public void step(Game game, float delta) {
                slothImage.setAngle(slothImage.getAngle() + .001f * delta);
                super.step(game, delta);
            }

            @Override
            public void render(Game game, Graphics graphics) {
                super.render(game, graphics);
                slothImage.render(graphics,200f, 200f);

                graphics.useCanvas(testCanvas);
                graphics.setColor(1f, 0f, 0f);
                graphics.drawRectangle(0, 0, 16, 16);
                graphics.popCanvas();

                graphics.setColor(1f, 0f, 0f);
                graphics.drawRectangle(8, 8, 30, 30);

                graphics.setAlpha(.5f);
                testCanvas.getImage().render(graphics, 200, 200);
                graphics.setAlpha(1f);
            }
        });
        game.run();
    }
}