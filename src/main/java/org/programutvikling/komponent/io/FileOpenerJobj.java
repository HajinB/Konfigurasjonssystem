package org.programutvikling.komponent.io;

import org.programutvikling.komponent.Komponent;
import org.programutvikling.komponent.KomponentRegister;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

    public class FileOpenerJobj implements FileOpener {
        @Override
        public void open(KomponentRegister komponentRegister, Path filePath) throws IOException {
            try (InputStream fin = Files.newInputStream(filePath);
                 ObjectInputStream oin = new ObjectInputStream(fin))
            {
                KomponentRegister register = (KomponentRegister) oin.readObject();
                register.removeAll();
                register.getRegister().forEach(komponentRegister::addKomponent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace(); // debug informasjon her
                throw new IOException("Something is wrong with loading of the jobj file. See debug information");
            }
        }
    }
