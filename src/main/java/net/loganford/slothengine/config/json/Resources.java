package net.loganford.slothengine.config.json;

import lombok.Data;

import java.util.List;

@Data
public class Resources {
    private List<ImageConfig> images;
    private List<FontConfig> fonts;
}