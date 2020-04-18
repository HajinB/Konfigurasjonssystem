package org.programutvikling.component.io;

import org.programutvikling.App;
import org.programutvikling.computer.Computer;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.gui.ContextModel;
import org.programutvikling.gui.Dialog;
import org.programutvikling.gui.utility.FileUtility;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileOpenerJobj implements FileOpener {
static int i = 0;
    public ArrayList<Object> open(ArrayList<Object> objects, Path selectedPath) {
        ArrayList<Object> listeInn = null;
        try (InputStream fin = Files.newInputStream(selectedPath);
             ObjectInputStream oin = new ObjectInputStream(fin)) {

            listeInn = (ArrayList<Object>) oin.readObject();
            System.out.println(i);
            i++;
            System.out.println("dette kastes til arrliobject: "+listeInn);

            objects.addAll(listeInn);
           // System.out.println("slik ser OBJECTS ut ( den som skal loades inn i minne) -etter opening : " + objects
            // .toString());
            //ContextModel.INSTANCE.getCleanObjectList().addAll(listeinn);
//dette gir NPE ?
        } catch (IOException | ClassNotFoundException i) {

            if(selectedPath.toString().equals("FileDirectory/Components/ComponentList.jobj")){
                FileUtility.deleteFile("FileDirectory/Components/ComponentList.jobj");
            }
            Dialog.showErrorDialog("filtype er feil");
            //Dialog.errorPopUp("Error", "filtype er feil", "kan ikke åpne filen - filtype må være jobj");
        }
        i=0;
        return listeInn;
    }
}