package net.loganford.slothengine.state.transition;

import lombok.Getter;
import net.loganford.slothengine.Game;
import net.loganford.slothengine.state.GameState;

public abstract class Transition<G extends Game> extends GameState {

    @Getter private GameState previousState;
    @Getter private GameState nextState;

    /**
     * Called prior to beginState() to set the previous and next state, and begin the next state.
     * @param game
     * @param previousState
     * @param nextState
     */
    public final void beginTransition(G game, GameState previousState, GameState nextState) {
        this.previousState = previousState;
        this.nextState = nextState;
        this.nextState.beginState(game);
        this.nextState.postBeginState(game);
    }

    /**
     * Ends the transition. At the next game loop, the state will be set to the next state.
     * @param game
     */
    public final void endTransition(Game game) {
        game.setState(nextState);
    }

    @Override
    public void endState(Game game) {
        super.endState(game);
        getPreviousState().endState(game);
    }
}