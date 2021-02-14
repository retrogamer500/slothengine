package net.loganford.slothengine.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class Resource {
    @Getter @Setter private String key;
    @Getter @Setter private List<String> tags = new ArrayList<>();
}
