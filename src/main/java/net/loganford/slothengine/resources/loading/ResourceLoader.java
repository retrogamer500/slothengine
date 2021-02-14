package net.loganford.slothengine.resources.loading;

import lombok.Getter;
import net.loganford.slothengine.Game;
import net.loganford.slothengine.config.json.LoadableConfig;
import net.loganford.slothengine.resources.Resource;

public abstract class ResourceLoader {
    @Getter private Game game;

    public ResourceLoader(Game game) {
        this.game = game;
    }

    public abstract void init(Game game);
    public abstract void loadOne(Game game);
    public abstract int getRemaining();

    protected void populateResource(Resource resource, LoadableConfig config) {
        resource.setKey(config.getKey());
        resource.setTags(config.getTags());
    }
}