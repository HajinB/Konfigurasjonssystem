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

    public ArrayList<Object> open(ArrayList<Object> objects, Path selectedPath) {
        try (InputStream fin = Files.newInputStream(selectedPath);
             ObjectInputStream oin = new ObjectInputStream(fin)) {

            ArrayList<Object> listeinn = (ArrayList<Object>) oin.readObject(); // kan kastes til Person
            objects.addAll(listeinn);
            //ContextModel.INSTANCE.getCleanObjectList().addAll(listeinn);
//dette gir NPE ?
            System.out.println("dette kastes til arrliobject: "+listeinn);
        } catch (IOException | ClassNotFoundException i) {

            if(selectedPath.toString().equals("FileDirectory/Components/ComponentList.jobj")){
                FileUtility.deleteFile("FileDirectory/Components/ComponentList.jobj");
            }
            Dialog.showErrorDialog("filtype er feil");
            //Dialog.errorPopUp("Error", "filtype er feil", "kan ikke åpne filen - filtype må være jobj");
        }
        return objects;
    }
}