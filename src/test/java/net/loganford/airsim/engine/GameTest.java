package net.loganford.airsim.engine;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    @Test
    public void testGame() {
        Game game = new Game(new GameState());
        game.run();
    }
}