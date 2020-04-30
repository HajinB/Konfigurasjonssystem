package org.programutvikling.component.io;

import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.ComponentValidator;
import org.programutvikling.computer.Computer;
import org.programutvikling.gui.utility.Dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileOpenerTxt implements FileOpener {
    ComponentValidator componentValidator = new ComponentValidator();
    boolean allLinesOk = true;

    public void open(Computer computer, Path filePath) throws IOException {
        computer.removeAll();
        //todo vi må få ut NAVN - BESKRIVELSE - "REGISTER" registeret består av componenter, så parsecomponent vil
        // fungere.
        //driter i navn og beskrivelse for computer, står ikke at man trenger det i oppgaveteksten..
        //finn de 2 første feltene navn og beskrivelse før man sender til parsecomponent.
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            String line;

            //.readline reads a line of text - så line er all tekst frem til "\n".
            // så kanskje før denne while loopen ha noe (og kanskje skriv computer sånn:
            //gaming pc;2999
            //
            //istedet for å legge til i computer - lager vi en temporary componentRegsiter

            ComponentRegister temp = new ComponentRegister();

            //hopper over første linje
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                //må man lage en metode som tar bort navn og description fra datamaskinen?
                //første to feltene feks er Navn og pris - så kommer componentregisteret - bør første line være NAVN;

                if(parseComponent(line).getProductType() == null){
                    //IKKE LES CURRENT - hvis getproducttype er null, ikke legg til i lista
                    bufferedReader.readLine(); //leser uten å gjøre noe
                }else{
                    computer.addComponent(parseComponent(line));
                    //temp.getRegister().add(parseComponent(line));
                }
            }
        }
       // ContextModel.INSTANCE.getComputer().getComponentRegister().getRegister().addAll(temp)
    }
    //todo eksempel fra tostring :

    private Component parseComponent(String line) throws InvalidComponentFormatException {
//hvordan skal man gjøre dette????? vi parser en component

        String[] split = line.split(";");
        if (split.length != 4) {
            throw new InvalidComponentFormatException("Du må bruke ; for å separere datafeltene.");
        }
        // extract all datafields from the string
        String type = split[0];
        String name = split[1];
        String description = split[2];
        double price = parseDouble(split[3], "pris må være et tall");
        //todo hvis man skal validere navn, type, beskrivelse, for å så bare endre pris til den som ligger i
        // databasen, kan man gjøre det her

        //altså bare valider alt i en metode - returner den komponenten som matcher i type, navn og beskrivelse, og
        // legg til den prisen som stemmer med "databasen"
        Component tempComponent = new Component(type, name, description, price);
        double priceState =
                ComponentValidator.checkPriceAgainstDatabaseGetPrice(tempComponent);
        if (priceState > 0) {
            //pris er feil - pricestate har riktig pris.
            Dialog.showErrorDialog("prisen på " + tempComponent.getProductName() +" stemmer ikke med databasen, vi " +
                    "endrer den til den riktige prisen");
            return new Component(type, name, description, priceState);
            //legg til dialog her evt.
        }
        if (priceState == -2.00) {
            //ingenting stemmer
            Dialog.showErrorDialog(tempComponent.getProductName() + " fins ikke i databasen vår, og blir dermed ikke " +
                    "lagt til i listen");
            return new Component(null, null, null, 0.00);
        }
        return new Component(type, name, description, price);
    }

    private double parseDouble(String str, String errorMessage) throws IllegalArgumentException {
        double number;
        try {
            number = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }

        return number;
    }

    @Override
    public ArrayList<Object> open(ArrayList<Object> list, Path filePath) throws IOException {
        return null;
    }
}
