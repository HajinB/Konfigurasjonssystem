package org.programutvikling.component.io;

import org.programutvikling.component.ComponentRegister;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSaverJobj implements FileSaver {
    @Override
    public void save(ComponentRegister componentRegister, Path filePath) throws IOException {
        try (OutputStream os = Files.newOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(os))
        {
            out.writeObject(componentRegister);
        }
    }
}
