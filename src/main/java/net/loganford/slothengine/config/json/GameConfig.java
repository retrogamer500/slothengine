package net.loganford.slothengine.config.json;

import lombok.Data;
import net.loganford.slothengine.utils.json.Required;

@Data
public class GameConfig {
    @Required
    private Resources resources;
}
