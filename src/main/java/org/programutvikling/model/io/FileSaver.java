package org.programutvikling.model.io;

import org.programutvikling.domain.computer.Computer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public interface FileSaver {

    void save(ArrayList<Object> componentRegister, Path filePath) throws IOException;

    void save(Computer computer, Path filePath) throws IOException;
}
