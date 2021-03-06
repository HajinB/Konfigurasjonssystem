package org.programutvikling.domain.io;

import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.FileUtility;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class FileOpenerJobj implements FileOpener {


    public ArrayList<Object> open(ArrayList<Object> objects, Path selectedPath) {
        ArrayList<Object> listeInn = null;
        try (InputStream fin = Files.newInputStream(selectedPath);
             ObjectInputStream oin = new ObjectInputStream(fin)) {
            listeInn = (ArrayList<Object>) oin.readObject();
            objects.addAll(listeInn);
        } catch (IOException | ClassNotFoundException i) {
            System.out.println(i.getMessage());
            System.out.println(Arrays.toString(i.getStackTrace()));
            if (selectedPath.toString().equals("FileDirectory/Database/AppDataBackup.jobj")) {
                FileUtility.deleteFile("FileDirectory/Database/AppDataBackup.jobj");
                //istedenfor å deletefile, last opp backup?
                Dialog.showErrorDialog("filtype er feil");
                System.out.println(i.getMessage());
                System.out.println(Arrays.toString(i.getStackTrace()));
            }
            System.out.println(Arrays.toString(i.getStackTrace()));
            System.out.println(i.getMessage());
            Dialog.showErrorDialog("filtype er feil");
        }
        return listeInn;
    }
}