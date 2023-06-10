package net.loganford.slothengine.resources.loading;

import net.loganford.slothengine.Game;
import net.loganford.slothengine.audio.Sound;
import net.loganford.slothengine.config.json.ImageConfig;
import net.loganford.slothengine.config.json.LoadableConfig;
import net.loganford.slothengine.config.json.SoundConfig;
import net.loganford.slothengine.graphics.Image;

import java.util.ArrayList;
import java.util.List;

public class SoundLoader extends ResourceLoader {

    private List<SoundConfig> soundsToLoad;

    public SoundLoader(Game game) {
        super(game);
    }

    @Override
    public void init(Game game) {
        soundsToLoad = new ArrayList<>();
        game.getImageManager().unloadOldResources(game);
        List<LoadableConfig> configs = new ArrayList<>(game.getConfigurationLoader().getConfig().getResources().getSounds());
        soundsToLoad.addAll((List<SoundConfig>) game.getSoundManager().getNeededResources(game, configs));
    }

    @Override
    public void loadOne(Game game) {
        SoundConfig config = soundsToLoad.remove(0);

        Sound sound = game.loadSound(config);
        populateResource(sound, config);
        game.getSoundManager().put(config.getKey(), sound);
    }

    @Override
    public int getRemaining() {
        return soundsToLoad.size();
    }
}
