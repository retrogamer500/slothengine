package net.loganford.slothengine.config.json;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SingleFileConfig extends LoadableConfig {
    private String filename;
}
