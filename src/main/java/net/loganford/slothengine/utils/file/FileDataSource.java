package net.loganford.slothengine.utils.file;

import lombok.Getter;
import net.loganford.slothengine.GameEngineException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileDataSource extends DataSource {

    @Getter private File file;

    public FileDataSource(File fileLocation) {
        file = fileLocation;
    }

    public FileDataSource(File folder, String location) {
        if(folder.isFile()) {
            throw new GameEngineException("Folder " + folder.getAbsolutePath() + " must be a valid directory.");
        }

        file = new File(folder.getAbsolutePath() + "/" + location);
    }

    @Override
    protected InputStream getInputStream() {
        try {
            return new FileInputStream(file);
        }
        catch(FileNotFoundException e) {
            throw new GameEngineException(e);
        }
    }

    @Override
    public boolean exists() {
        return file.exists();
    }
}