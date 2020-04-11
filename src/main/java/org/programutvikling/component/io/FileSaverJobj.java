package org.programutvikling.component.io;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

//Programmet skal støtte filbehandling slik at programmets data ikke går tapt når programmet avsluttes.
//Det anbefales at besvarelsen allerede inneholder eksempeldata slik at sensor har noe å gå ut ifra.
//
//Det forventes at bruker ikke må bruke mye tid på valg av plassering av filene. Filplasseringen kan for
//eksempel være pre-definert eller programmet kan la bruker få velge hvilken mappe filene skal lagres
//i.
//
// Til sammenlikning, har noen besvarelser fra tidligere år krevd at bruker velger filplasseringen til 10+
//ulike konfigurasjonsfiler med FileChooser hver gang applikasjonen starter (dette er ikke spesielt
//brukervennlig).

//Lagring av data som kommer fra superbruker skal lagres med binære filer, mens data fra sluttbruker
//skal lagres med tekstfiler. Tekstfilene skal være kompatible med Excel, slik at produkter kan
//prosesseres med andre programmer.

//Sensor kommer til å teste programmet ved å injisere ugyldig data i filene, så husk å inkludere
//håndtering av ugyldig data fra fil.

//Metoden som laster inn data på superbrukerens side skal gjennomføres i en egen tråd. Legg til noen
//sekunder med venting slik at tråd-løsningen kan verifiseres av sensor. Det skal ikke være mulig å legge
//til eller endre på elementer i brukergrensesnittet mens tråden arbeider.


//lagrer kun et array - kan ikke lagre pc eller en komponent(?)

//https://stackoverflow.com/questions/20261986/write-to-file-using-objectoutputstream-without-overwriting-old-data


//But Java serialization does not support "appending".
// you can't write an ObjectOutputStream to a file,
// then open the file again in append mode and write another ObjectOutputStream to it.
// you have to re-write the entire file every time.
// (i.e. if you want to add objects to the file,
// you need to read all the existing objects,
// then write the file again with all the old objects and then the new objects).
public class FileSaverJobj implements FileSaver {

    /*
    @Override
    public void save(ComponentRegister componentRegister, Path filePath) throws IOException {
        try (OutputStream os = Files.newOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(os))
        {
            out.writeObject(componentRegister);
        }
    }
}*/





    @Override
    public void save(ArrayList<Object> objects, Path filePath) throws IOException {
        try (OutputStream os = Files.newOutputStream(filePath);

             //try( FileOutputStream os = (FileOutputStream) Files.newOutputStream(filePath);
             //try(FileOutputStream os = new FileOutputStream(String.valueOf(Files.newOutputStream(filePath)), false);
             ObjectOutputStream out = new ObjectOutputStream(os)) {
            //out.flush();
            //out.writeObject(componentRegister);
            //out.writeObject(Filbehandling.convertListToReadable(personRegister.personArrayList));

            out.reset();
            //out.writeObject(componentRegister);
            out.writeObject(objects);

            out.flush();

            out.close();
        }
    }
}
    /*
    @Override
    public void save(componentRegister componentRegister, Path filePath) throws IOException {
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(String.valueOf(Paths.get(String.valueOf(filePath))));
            oos = new ObjectOutputStream(fout);
            System.out.println(oos.getClass().toString());
            System.out.println(fout.getFD());
            oos.flush();
            fout.flush();

            //hvorfor gir denne nullpointerexception når man har åpnaprogrammet på nytt??????
            oos.writeObject(componentRegister);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }
}
*/


/*
        try (OutputStream os = Files.newOutputStream(Paths.get(selectedFile.getPath()));
                //creates an outputstream to the chosen path
                ObjectOutputStream out = new ObjectOutputStream(os))
                //creates an object that is pure stream
                {
                out.writeObject(Logikk.convertListToReadable(personRegister.personArrayList));
                //writes the person-array to jobj
                }
*/