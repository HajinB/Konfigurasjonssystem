package org.programutvikling.domain.io;

import org.programutvikling.domain.computer.Computer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public interface FileSaver {

    void save(ArrayList<Object> componentRegister, Path filePath) throws IOException;
}
