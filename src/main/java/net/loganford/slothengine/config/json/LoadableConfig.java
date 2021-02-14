package net.loganford.slothengine.config.json;

import lombok.Data;
import net.loganford.slothengine.utils.file.ResourceMapper;
import net.loganford.slothengine.utils.json.Required;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class LoadableConfig implements Cloneable {
    private transient ResourceMapper resourceMapper;
    @Required
    private String key;
    private List<String> tags = new ArrayList<>();

    public LoadableConfig clone() throws CloneNotSupportedException {
        return (LoadableConfig) super.clone();
    }
}
