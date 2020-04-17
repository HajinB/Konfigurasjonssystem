package org.programutvikling.component.io;


import org.programutvikling.computer.Computer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileSaverTxt implements FileSaver {
    public void save(Computer computer, Path filePath) throws IOException {
        Files.write(filePath, computer.toString().getBytes());
    }

    @Override
    public void save(ArrayList<Object> componentRegister, Path filePath) throws IOException {
        Files.write(filePath, componentRegister.toString().getBytes());
    }
}