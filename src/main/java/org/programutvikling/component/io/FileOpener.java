package org.programutvikling.component.io;

import org.programutvikling.computer.Computer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public interface FileOpener {

    ArrayList<Object> open(ArrayList<Object> list, Path filePath) throws IOException;

}