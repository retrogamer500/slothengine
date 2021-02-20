package net.loganford.slothengine.state.loading;

import lombok.Getter;
import lombok.Setter;
import net.loganford.slothengine.Game;
import net.loganford.slothengine.resources.loading.ResourceLoader;
import net.loganford.slothengine.state.GameState;

import java.util.List;

public abstract class LoadingScreen extends GameState {
    @Getter private GameState nextState;
    @Getter private List<ResourceLoader> resourceLoaders;
    @Getter private int totalItems = 0;
    @Getter private int itemsToLoad = 0;
    @Getter private float ratioLoaded = 0f;

    private boolean vsync;
    private int minFps;
    private int maxFps;

    public final void beginLoadingScreen(GameState nextState) {
        this.nextState = nextState;
    }

    @Override
    public void beginState(Game game) {
        super.beginState(game);
        resourceLoaders = game.getResourceLoaders();

        for(ResourceLoader loader: resourceLoaders) {
            loader.init(game);
            totalItems += loader.getRemaining();
        }
        itemsToLoad = totalItems;

        minFps = game.getMinFps();
        maxFps = game.getMaxFps();
        game.setFps(1, 9999);
        vsync = game.getGraphics().isVsync();
        game.getGraphics().setVsync(false);
    }

    @Override
    public void step(Game game, float delta) {
        super.step(game, delta);

        {
            //Get the first loader with a resource
            ResourceLoader loader = resourceLoaders.get(0);
            while (loader.getRemaining() == 0) {
                if (resourceLoaders.size() > 1) {
                    resourceLoaders.remove(0);
                    loader = resourceLoaders.get(0);
                }
                else {
                    break;
                }
            }

            //Load a resource
            if (loader.getRemaining() > 0) {
                loader.loadOne(game);
            } else {
                if (resourceLoaders.size() > 1) {
                    resourceLoaders.remove(0);
                } else {
                    doneLoading(game);
                }
            }
        }

        //Refresh the percentage of items loaded
        itemsToLoad = 0;
        for(ResourceLoader loader: resourceLoaders) {
            itemsToLoad += loader.getRemaining();
        }

        ratioLoaded = 1f - (totalItems == 0 ? 0f : (itemsToLoad / (float)totalItems));
    }

    public final void doneLoading(Game game) {
        game.getGraphics().setVsync(vsync);
        game.setFps(minFps, maxFps);
        game.setState(nextState);
    }
}