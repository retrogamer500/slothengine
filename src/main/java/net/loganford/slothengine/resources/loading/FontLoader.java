package net.loganford.slothengine.resources.loading;

import lombok.extern.log4j.Log4j2;
import net.loganford.slothengine.Game;
import net.loganford.slothengine.config.json.FontConfig;
import net.loganford.slothengine.config.json.ImageConfig;
import net.loganford.slothengine.config.json.LoadableConfig;
import net.loganford.slothengine.graphics.Font;
import net.loganford.slothengine.graphics.Image;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Log4j2
public class FontLoader extends ResourceLoader{

    private List<FontConfig> fontsToLoad;

    public FontLoader(Game game) {
        super(game);
    }

    @Override
    public void init(Game game) {
        fontsToLoad = new ArrayList<>();
        game.getFontManager().unloadOldResources(game);

        List<LoadableConfig> configs = new ArrayList<>(game.getConfigurationLoader().getConfig().getResources().getFonts());
        fontsToLoad.addAll((List<FontConfig>) game.getImageManager().getNeededResources(game, configs));
    }

    @Override
    public void loadOne(Game game) {
        FontConfig config = fontsToLoad.remove(0);
        Font font = game.getGraphics().loadFont(config);
        populateResource(font, config);
        game.getFontManager().put(config.getKey(), font);
    }

    @Override
    public int getRemaining() {
        return fontsToLoad.size();
    }
}
