package net.loganford.slothengine;

import net.loganford.slothFx.FxGame;
import net.loganford.slothengine.graphics.Graphics;
import net.loganford.slothengine.graphics.Image;
import net.loganford.slothengine.state.GameState;
import org.junit.Test;

public class GameTest {
    @Test
    public void testGame() {
        FxGame game = new FxGame(new GameState() {

            private Image slothImage;

            @Override
            public void beginState(Game game) {
                super.beginState(game);
                game.setFps(30, 60);
                slothImage = game.getImageManager().get("sloth");
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
            }
        });
        game.run();
    }
}