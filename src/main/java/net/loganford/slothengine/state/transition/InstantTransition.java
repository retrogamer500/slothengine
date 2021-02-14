package net.loganford.slothengine.state.transition;

import net.loganford.slothengine.Game;

public class InstantTransition extends Transition {
    @Override
    public void beginState(Game game) {
        super.beginState(game);
        endTransition(game);
    }
}