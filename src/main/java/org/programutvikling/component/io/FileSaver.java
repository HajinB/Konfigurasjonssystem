package org.programutvikling.component.io;

import org.programutvikling.component.ComponentRegister;

import java.io.IOException;
import java.nio.file.Path;

public interface FileSaver {

    void save(ComponentRegister componentRegister, Path filePath) throws IOException;

}
