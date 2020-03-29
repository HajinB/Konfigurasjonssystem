package org.programutvikling.komponent.io;

import org.programutvikling.komponent.KomponentRegister;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSaverJobj implements FileSaver {
    @Override
    public void save(KomponentRegister komponentRegister, Path filePath) throws IOException {
        try (OutputStream os = Files.newOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(os))
        {
            out.writeObject(komponentRegister);
        }
    }
}
