package net.loganford.slothengine.resources;

import net.loganford.slothengine.Game;
import net.loganford.slothengine.GameEngineException;
import net.loganford.slothengine.config.json.LoadableConfig;

import java.util.*;
import java.util.stream.Collectors;

public class ResourceManager<T extends Resource> {
    private Map<String, T> resources;

    public ResourceManager() {
        resources = new HashMap<>();
    }

    public void unloadOldResources(Game game) {
        Iterator<Map.Entry<String, T>> iterator = resources.entrySet().iterator();
        while(iterator.hasNext()) {
            T resource = iterator.next().getValue();
            List<String> intersection = resource.getTags().stream()
                    .filter(game.getLoadedResourceTags()::contains)
                    .collect(Collectors.toList());
            if(intersection.size() == 0) {
                iterator.remove();

                //Todo: perform resource cleanup here
            }
        }
    }

    public List<? extends LoadableConfig> getNeededResources(Game game, List<LoadableConfig> loadableConfigs) {
        List<LoadableConfig> returnList = new ArrayList<>();
        return loadableConfigs.stream()
                .filter(e -> !Collections.disjoint(e.getTags(), game.getLoadedResourceTags()))
                .filter(e -> !resources.containsKey(e.getKey())).collect(Collectors.toList());
    }

    public void put(String key, T resource) {
        if(resources.containsKey(key)) {
            throw new GameEngineException("Entity manager contains key: " + key + ".");
        }
        else {
            resources.put(key, resource);
        }
    }

    @SuppressWarnings("unchecked")
    public T get(String key) {
        T resource = resources.get(key);
        if(resource == null) {
            throw new GameEngineException("Resource does not exist: " + key);
        }

        try {
            if (resource instanceof PrototypeResource) {
                return (T)((PrototypeResource) resource).clone();
            }
        }
        catch(CloneNotSupportedException e) {
            throw new GameEngineException(e);
        }

        return resource;
    }

    public boolean exists(String key) {
        return resources.containsKey(key);
    }
}
