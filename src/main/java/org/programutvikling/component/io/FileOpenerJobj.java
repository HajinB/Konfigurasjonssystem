package org.programutvikling.component.io;

import org.programutvikling.component.ComponentRegister;
import org.programutvikling.gui.Dialog;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.ComponentValidator;

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
    public void open(ComponentRegister componentRegister, Path filePath) throws IOException {
        try (InputStream fin = Files.newInputStream(filePath);
             ObjectInputStream oin = new ObjectInputStream(fin))
        {
            ComponentRegister register = (ComponentRegister) oin.readObject();
            //register.removeAll();
            register.getRegister().forEach(componentRegister::addComponent);
            System.out.println();
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // debug information here
            throw new IOException("Something is wrong with the implementation. See debug information");
        }
    }
}


/*
    @Override
    public void open(componentRegister componentRegister, Path filePath) throws IOException {

        File f = new File(String.valueOf(filePath));

       // try(InputStream fin = Files.newInputStream(filePath);
        try (FileInputStream fin = new FileInputStream(f);
             ObjectInputStream oin = new ObjectInputStream(fin)) {
            componentRegister komponentInput = (componentRegister) oin.readObject();
            //register.removeAll();
            componentRegister.getRegister().addAll(komponentInput.getRegister());

            componentRegister.log();            //componentRegister.addKomponent(register.getRegister().get(0));
            //register.getRegister().forEach(componentRegister::addKomponent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // debug informasjon her
            throw new IOException("Something is wrong with loading of the jobj file. See debug information");
        }
    }
}


/*
    @Override DENNE FUNGERER EGENTLIG:
    public void open(componentRegister componentRegister, Path selectedPath) {
        try (InputStream fin = Files.newInputStream(selectedPath);
             ObjectInputStream oin = new ObjectInputStream(fin)) {
            List<Komponent> listeinn = (List<Komponent>) oin.readObject(); // kan kastes til Person
            //System.out.println(personlista);
            componentRegister.getRegister().addAll(listeinn);
            //componentRegister.getRegister().add((Komponent) listeinn);
        } catch (IOException | ClassNotFoundException i) {
            Dialog.showErrorDialog("filtype er feil");
            //Dialog.errorPopUp("Error", "filtype er feil", "kan ikke åpne filen - filtype må være jobj");
        }

    }
}
*/

/*
    @Override
    public void open(componentRegister componentRegister, Path filePath) throws IOException {
        try (InputStream fin = Files.newInputStream(filePath);
             ObjectInputStream oin = new ObjectInputStream(fin))
        {
            componentRegister register = (componentRegister) oin.readObject();
            componentRegister.removeAll();
            register.getRegister().forEach(componentRegister::addKomponent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // debug information here
            throw new IOException("Something is wrong with the implementation. See debug information");
        }
    }


    }
    /*
 */
