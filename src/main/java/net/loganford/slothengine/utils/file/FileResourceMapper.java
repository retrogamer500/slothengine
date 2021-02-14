package net.loganford.slothengine.utils.file;

import java.io.File;


public class FileResourceMapper extends ResourceMapper {
    private File folder;

    public FileResourceMapper(File folder) {
        this.folder = folder;
    }

    @Override
    public DataSource get(String resourceKey) {
        return new FileDataSource(folder, resourceKey);
    }
}