package org.programutvikling.domain.io;


import org.programutvikling.domain.computer.Computer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileSaverTxt implements FileSaver {
    public void save(Computer computer, Path filePath) throws IOException {
        Files.write(filePath, computer.toString().getBytes());
    }

    public void save(ArrayList<Object> componentRegister, Path filePath) throws IOException {
        Files.write(filePath, componentRegister.toString().getBytes());
    }
}