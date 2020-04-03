package org.programutvikling.komponent.io;

import org.programutvikling.gui.Dialog;
import org.programutvikling.komponent.Komponent;
import org.programutvikling.komponent.KomponentRegister;
import org.programutvikling.komponent.KomponentValidator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileOpenerJobj implements FileOpener {


    @Override
    public void open(KomponentRegister komponentRegister, Path filePath) throws IOException {
        try (InputStream fin = Files.newInputStream(filePath);
             ObjectInputStream oin = new ObjectInputStream(fin))
        {
            KomponentRegister register = (KomponentRegister) oin.readObject();
            //register.removeAll();
            register.getRegister().forEach(komponentRegister::addKomponent);
            System.out.println();
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // debug information here
            throw new IOException("Something is wrong with the implementation. See debug information");
        }
    }
}


/*
    @Override
    public void open(KomponentRegister komponentRegister, Path filePath) throws IOException {

        File f = new File(String.valueOf(filePath));

       // try(InputStream fin = Files.newInputStream(filePath);
        try (FileInputStream fin = new FileInputStream(f);
             ObjectInputStream oin = new ObjectInputStream(fin)) {
            KomponentRegister komponentInput = (KomponentRegister) oin.readObject();
            //register.removeAll();
            komponentRegister.getRegister().addAll(komponentInput.getRegister());

            komponentRegister.log();            //komponentRegister.addKomponent(register.getRegister().get(0));
            //register.getRegister().forEach(komponentRegister::addKomponent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // debug informasjon her
            throw new IOException("Something is wrong with loading of the jobj file. See debug information");
        }
    }
}


/*
    @Override DENNE FUNGERER EGENTLIG:
    public void open(KomponentRegister komponentRegister, Path selectedPath) {
        try (InputStream fin = Files.newInputStream(selectedPath);
             ObjectInputStream oin = new ObjectInputStream(fin)) {
            List<Komponent> listeinn = (List<Komponent>) oin.readObject(); // kan kastes til Person
            //System.out.println(personlista);
            komponentRegister.getRegister().addAll(listeinn);
            //komponentRegister.getRegister().add((Komponent) listeinn);
        } catch (IOException | ClassNotFoundException i) {
            Dialog.showErrorDialog("filtype er feil");
            //Dialog.errorPopUp("Error", "filtype er feil", "kan ikke åpne filen - filtype må være jobj");
        }

    }
}
*/

/*
    @Override
    public void open(KomponentRegister komponentRegister, Path filePath) throws IOException {
        try (InputStream fin = Files.newInputStream(filePath);
             ObjectInputStream oin = new ObjectInputStream(fin))
        {
            KomponentRegister register = (KomponentRegister) oin.readObject();
            komponentRegister.removeAll();
            register.getRegister().forEach(komponentRegister::addKomponent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // debug information here
            throw new IOException("Something is wrong with the implementation. See debug information");
        }
    }


    }
    /*
 */
