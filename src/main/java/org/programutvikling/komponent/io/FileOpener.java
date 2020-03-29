package org.programutvikling.komponent.io;

import org.programutvikling.komponent.KomponentRegister;

import java.io.IOException;
import java.nio.file.Path;

public interface FileOpener {

    void open(KomponentRegister komponentRegister, Path filePath) throws IOException;

}
