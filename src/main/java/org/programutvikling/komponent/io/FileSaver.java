package org.programutvikling.komponent.io;

import org.programutvikling.komponent.Komponent;
import org.programutvikling.komponent.KomponentRegister;

import java.io.IOException;
import java.nio.file.Path;

public interface FileSaver {

    void save(KomponentRegister komponentRegister, Path filePath) throws IOException;

}
