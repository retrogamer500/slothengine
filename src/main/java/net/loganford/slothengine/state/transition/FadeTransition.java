package net.loganford.slothengine.state.transition;

import net.loganford.slothengine.Game;
import net.loganford.slothengine.graphics.Graphics;

public class FadeTransition extends Transition {
    private long duration;
    private long timer;

    public FadeTransition(long duration) {
        this.duration = duration;
    }

    @Override
    public void beginState(Game game) {
        super.beginState(game);
        getPreviousState().renderState(game, game.getGraphics());
        getNextState().renderState(game, game.getGraphics());
    }

    @Override
    public void step(Game game, float delta) {
        super.step(game, delta);
        timer+= delta;

        if(timer > duration) {
            endTransition(game);
        }
    }

    @Override
    public void render(Game game, Graphics graphics) {
        super.render(game, graphics);

        float alpha = timer == 0 ? 0 : Math.min(((float)timer) / duration, 1);

        getPreviousState().getStateCanvas().getImage().render(graphics, 0, 0);

        graphics.setAlpha(alpha);
        getNextState().getStateCanvas().getImage().render(graphics, 0, 0);
        graphics.setAlpha(1f);
    }
}
