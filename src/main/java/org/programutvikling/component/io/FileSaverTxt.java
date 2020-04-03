package org.programutvikling.component.io;


import org.programutvikling.component.ComponentRegister;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileSaverTxt implements FileSaver {
    @Override
    public void save(ComponentRegister reg, Path filePath) throws IOException {
        Files.write(filePath, reg.toString().getBytes());
    }

}