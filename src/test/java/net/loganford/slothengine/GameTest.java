package net.loganford.slothengine;

import net.loganford.slothengine.state.GameState;
import org.junit.Test;

public class GameTest {
    @Test
    public void testGame() {
        Game game = new Game(new GameState());
        game.run();
    }
}