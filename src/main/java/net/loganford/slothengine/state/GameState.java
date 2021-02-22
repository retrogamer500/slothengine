package net.loganford.slothengine.state;

import lombok.Getter;
import net.loganford.slothFx.FxCanvas;
import net.loganford.slothFx.FxGraphics;
import net.loganford.slothengine.Game;
import net.loganford.slothengine.graphics.Canvas;
import net.loganford.slothengine.graphics.Graphics;
import net.loganford.slothengine.resources.LoadTag;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    @Getter private Canvas canvas;

    public void beginState(Game game) {
        canvas = game.getGraphics().createCanvas(640, 480); //Todo- grab size from game
    }

    public void postBeginState(Game game) {}

    public final void stepState(Game game, float delta) {
        step(game, delta);
    }

    public void step(Game game, float delta) {

    }

    public final void renderState(Game game, Graphics graphics) {
        canvas.use();
        graphics.clear();
        render(game, graphics);
        graphics.getScreenCanvas().use();
        canvas.getImage().render(graphics, 0, 0);
    }

    public void render(Game game, Graphics graphics) {

    }

    public void endState(Game game) {

    }

    /**
     * Returns a List of resource tags required by this level. If this contains a resource group which is not currently
     * loaded by the game, then prior to loading this state, a loading screen will appear and the required resources
     * will be loaded.
     * @return a list of resources which the state requires
     */
    public List<String> getRequiredTags() {
        List<String> requiredTags = new ArrayList<>();
        requiredTags.add(null);

        for (Annotation annotation : getClass().getAnnotations()) {
            if(annotation instanceof LoadTag.List) {
                LoadTag.List loadTagList = (LoadTag.List) annotation;
                for(LoadTag loadTag: loadTagList.value()) {
                    requiredTags.add(loadTag.value());
                }
            }
        }

        return requiredTags;
    }
}
