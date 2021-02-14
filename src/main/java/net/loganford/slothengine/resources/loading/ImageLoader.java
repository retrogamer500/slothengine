package net.loganford.slothengine.resources.loading;

import lombok.extern.log4j.Log4j2;
import net.loganford.slothengine.Game;
import net.loganford.slothengine.config.json.ImageConfig;
import net.loganford.slothengine.config.json.LoadableConfig;
import net.loganford.slothengine.graphics.Image;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Log4j2
public class ImageLoader extends ResourceLoader{

    private List<ImageConfig> imagesToLoad;

    public ImageLoader(Game game) {
        super(game);
    }

    @Override
    public void init(Game game) {
        imagesToLoad = new ArrayList<>();
        game.getImageManager().unloadOldResources(game);
        List<LoadableConfig> configs = new ArrayList<>(game.getConfigurationLoader().getConfig().getResources().getImages());
        imagesToLoad.addAll((List<ImageConfig>) game.getImageManager().getNeededResources(game, configs));
    }

    @Override
    public void loadOne(Game game) {
        ImageConfig config = imagesToLoad.remove(0);
        Image image = game.getGraphics().loadImage(config);
        populateResource(image, config);
        game.getImageManager().put(config.getKey(), image);
    }

    @Override
    public int getRemaining() {
        return imagesToLoad.size();
    }
}
