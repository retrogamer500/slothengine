package net.loganford.slothengine.config;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.loganford.slothengine.GameEngineException;
import net.loganford.slothengine.config.json.GameConfig;
import net.loganford.slothengine.config.json.LoadableConfig;
import net.loganford.slothengine.utils.file.DataSource;
import net.loganford.slothengine.utils.file.ResourceMapper;
import net.loganford.slothengine.utils.json.JsonValidator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ConfigurationLoader {
    @Getter @Setter private Class<? extends GameConfig> configurationClass = GameConfig.class;
    @Getter private GameConfig config;
    private Gson gson;

    public ConfigurationLoader() {
        gson = new Gson();
    }

    public void load(ResourceMapper resourceMapper, DataSource configSource) {
        try {
            if (!configSource.exists()) {
                throw new GameEngineException("No configuration file exists.");
            }

            log.info("Loading configuration file: " + configSource);
            String json = configSource.load();
            GameConfig loadedConfig = gson.fromJson(json, configurationClass);
            JsonValidator.validateThenThrow(loadedConfig);
            populateResourceMappers(loadedConfig, resourceMapper);

            this.config = loadedConfig;
        }
        catch(Exception e) {
            throw new GameEngineException(e);
        }
    }

    private void populateResourceMappers(GameConfig gameConfig, ResourceMapper resourceMapper) {
        for(LoadableConfig config : getConfigsWithClass(gameConfig, LoadableConfig.class)) {
            config.setResourceMapper(resourceMapper);

            if(config.getTags() == null) {
                config.setTags(new ArrayList<>());
            }
            if(config.getTags().size() == 0) {
                config.getTags().add(null);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <C extends LoadableConfig> List<C> getConfigsWithClass(GameConfig gameConfig, Class<C> clazz) {
        List<C> returnList = new ArrayList<>();

        try {
            for (Field listField : gameConfig.getResources().getClass().getDeclaredFields()) {
                listField.setAccessible(true);
                if (List.class.isAssignableFrom(listField.getType())) {
                    List list = (List) listField.get(gameConfig.getResources());
                    if (list != null && list.size() > 0) {
                        Object first = list.get(0);
                        if (clazz.isAssignableFrom(first.getClass())) {
                            for(Object object : list) {
                                returnList.add((C)object);
                            }
                        }
                    }
                }
            }
        }
        catch(IllegalAccessException e) {
            throw new GameEngineException("Unable to process game configuration.", e);
        }

        return returnList;
    }
}
