package org.programutvikling.component.io;

import org.programutvikling.computer.Computer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
public class FileSaverJobj implements FileSaver {
    @Override
    public void save(ArrayList<Object> objects, Path filePath) throws IOException {
        try (OutputStream os = Files.newOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(os)) {

            System.out.println("Dette blir lagret"+objects);
            out.reset();

            out.writeObject(objects);

            out.flush();

        }
    }

    @Override
    public void save(Computer computer, Path filePath) throws IOException {

    }
}