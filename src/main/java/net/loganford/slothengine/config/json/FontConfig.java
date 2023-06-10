package net.loganford.slothengine.config.json;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.loganford.slothengine.utils.json.Required;

@Data
@EqualsAndHashCode(callSuper=true)
public class FontConfig extends SingleFileConfig {
    @Required
    public float size;
}
