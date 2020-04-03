package org.programutvikling.komponent.io;


import org.programutvikling.komponent.KomponentRegister;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileSaverTxt implements FileSaver {
    @Override
    public void save(KomponentRegister reg, Path filePath) throws IOException {
        Files.write(filePath, reg.toString().getBytes());
    }

}