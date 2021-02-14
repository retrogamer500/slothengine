package net.loganford.slothengine.resources;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Repeatable(LoadTag.List.class)
public @interface LoadTag {
    String value();

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    @interface List {
        LoadTag[] value();
    }
}
