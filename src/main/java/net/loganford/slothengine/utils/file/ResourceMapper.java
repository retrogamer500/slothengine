package net.loganford.slothengine.utils.file;

import lombok.extern.log4j.Log4j2;

/**
 * A resource mapper allows the conversion between a string and a resource. This abstracts away the method of how game
 * resources are stored and allows us to store these resources in files, zip files, within the jar, or potentially
 * anywhere else.
 */
@Log4j2
public abstract class ResourceMapper {
    /**
     * Converts a string into a resource.
     * @param resourceKey a string representing the resource
     * @return the resource, all ready for reading data
     */
    public abstract DataSource get(String resourceKey);
}
