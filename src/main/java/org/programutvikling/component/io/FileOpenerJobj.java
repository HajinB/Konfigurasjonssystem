package org.programutvikling.component.io;

import org.programutvikling.component.ComponentRegister;
import org.programutvikling.computer.Computer;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.gui.Dialog;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.ComponentValidator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileOpenerJobj implements FileOpener {


   /* @Override
    public void open(ComponentRegister componentRegister, Path filePath) throws IOException {
        try (InputStream fin = Files.newInputStream(filePath);
             ObjectInputStream oin = new ObjectInputStream(fin))
        {
            ComponentRegister register = (ComponentRegister) oin.readObject();
            //register.removeAll();
            register.getRegister().forEach(componentRegister::addComponent);
            register.log();
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // debug information here
            throw new IOException("Something is wrong with the implementation. See debug information");
        }
    }*/

    @Override
    public void open(Computer computer, Path filePath) throws IOException {

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
*/


    //bør open være objekt istedet ? også kan vi lage en array som holder mange samtidig ??????????
    // = profit

    //så det egentlige problemet med å lage en ArrayList<Object> er tilbakesendingen - altså at controlleren skal
    // sende de objektene som skal åpnes til denne metoden under her,  (slik vi gjør med componentregister her) -
    public void open(ArrayList<Object> objects, Path selectedPath) {
        try (InputStream fin = Files.newInputStream(selectedPath);
             ObjectInputStream oin = new ObjectInputStream(fin)) {

            ArrayList<Object> listeinn = (ArrayList<Object>) oin.readObject(); // kan kastes til Person
            //System.out.println(personlista);
            objects.clear();
            objects.addAll(listeinn);
            //componentRegister.getRegister().add((Komponent) listeinn);

        } catch (IOException | ClassNotFoundException i) {
            Dialog.showErrorDialog("filtype er feil");
            //Dialog.errorPopUp("Error", "filtype er feil", "kan ikke åpne filen - filtype må være jobj");
        }
    }
            //fins det bedre måter enn dette? vha polymorfisme?
    public void open(ComputerRegister computerRegister, Path selectedPath) {


        try (InputStream fin = Files.newInputStream(selectedPath);
             ObjectInputStream oin = new ObjectInputStream(fin)) {

            ComputerRegister listeinn = (ComputerRegister) oin.readObject(); // kan kastes til Person
            //System.out.println(personlista);
            //computerRegister.getRegister().addAll(listeinn);
            listeinn.getRegister().forEach(computerRegister::addComponent);

            //componentRegister.getRegister().add((Komponent) listeinn);
        } catch (IOException | ClassNotFoundException i) {
            Dialog.showErrorDialog("filtype er feil");
            //Dialog.errorPopUp("Error", "filtype er feil", "kan ikke åpne filen - filtype må være jobj");
        }
    }
            //fins det bedre måter enn dette? vha polymorfisme?
    public void open(ComputerRegister computerRegister, Path selectedPath) {
        try (InputStream fin = Files.newInputStream(selectedPath);
             ObjectInputStream oin = new ObjectInputStream(fin)) {

            ComputerRegister listeinn = (ComputerRegister) oin.readObject(); // kan kastes til Person
            //System.out.println(personlista);
            //computerRegister.getRegister().addAll(listeinn);
            listeinn.getRegister().forEach(computerRegister::addComponent);

            //componentRegister.getRegister().add((Komponent) listeinn);
        } catch (IOException | ClassNotFoundException i) {
            Dialog.showErrorDialog("filtype er feil");
            //Dialog.errorPopUp("Error", "filtype er feil", "kan ikke åpne filen - filtype må være jobj");
        }
    }
}
/*
    @Override
    public void open(ComponentRegister componentRegister, Path filePath) throws IOException {
        try (InputStream fin = Files.newInputStream(filePath);
             ObjectInputStream oin = new ObjectInputStream(fin))
        {
            ComponentRegister registerInput = (ComponentRegister) oin.readObject();
            componentRegister.removeAll();
            //registerInput.getRegister().forEach(componentRegister::addComponent);
            //componentRegister.getRegister().add((Component) registerInput);
            componentRegister.getRegister().addAll(registerInput.getRegister());
            System.out.println(componentRegister.toString());

        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // debug information here
            throw new IOException("Something is wrong with the implementation. See debug information");
        }
    }
}
*/