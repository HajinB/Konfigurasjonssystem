package org.programutvikling.component.io;

import org.programutvikling.computer.Computer;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.gui.Converter;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.io.FileOpener;



import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileOpenerTxt implements FileOpener {

    @Override
    public void open(ArrayList<Object> list, Path filePath) throws IOException {
        list.clear();
        // try-with-resources lukker automatisk filen

        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                list.add(parseComponent(line));
            }
        }
    }


    @Override

    //skal man åpne et computerregister (((skal egentlig være automatisk loada fra før - så brukeren har vel bare
    // muglihet til å laste inn og lagre EN og en computer?
    public void open(Computer computer, Path filePath) throws IOException {
        computer.removeAll();
        // try-with-resources lukker automatisk filen


        //todo vi må få ut NAVN - BESKRIVELSE - "REGISTER" registeret består av componenter, så parsecomponent vil
        // fungere.


        //driter i navn og beskrivelse for computer, står ikke at man trenger det i oppgaveteksten..



        //finn de 2 første feltene navn og beskrivelse før man sender til parsecomponent.
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                //må man lage en metode som tar bort navn og description fra datamaskinen?
                computer.addComponent(parseComponent(line));
            }
        }
    }


    //todo eksempel fra tostring :

    //Skriv de på forskjellige lines?
    // Gamingpc, bra gaming pc ass bro,
    // cpu1, cpu intel, bra cpu, 299,
     //harddisk, harddisk ssd, 2999

    //jobbPC, bra jobbpc bruh


    //(String type, String name, String description, double price)
    private Component parseComponent(String line) throws InvalidComponentFormatException {
//hvordan skal man gjøre dette????? vi parser en component

        String[] split = line.split(",");
        if(split.length != 4) {
            throw new InvalidComponentFormatException("Du må bruke , for å separere datafeltene.");
        }

        // extract all datafields from the string
        String type = split[0];
        String name = split[1];
        String description = split[2];
        double price = parseDouble(split[3], "pris må være et tall");

        //try {
            //double price = doubleStringConverter.stringTilDouble(split[3]);
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
}
