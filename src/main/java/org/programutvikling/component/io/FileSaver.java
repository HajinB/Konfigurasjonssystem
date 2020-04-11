package org.programutvikling.component.io;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public interface FileSaver {

    void save(ArrayList<Object> componentRegister, Path filePath) throws IOException;

}
