package org.programutvikling.komponent.io;

import org.programutvikling.gui.Converter;
import org.programutvikling.komponent.Komponent;
import org.programutvikling.komponent.KomponentRegister;
import org.programutvikling.komponent.io.FileOpener;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileOpenerTxt implements FileOpener {
    Converter.DoubleStringConverter doubleStringConverter;

    @Override
    public void open(KomponentRegister register, Path filePath) throws IOException {
        register.removeAll();
        // try-with-resources lukker automatisk filen
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                register.addKomponent(parseKomponent(line));
            }
        }
    }
//(String type, String name, String description, double price)
    private Komponent parseKomponent(String line) throws InvalidComponentFormatException {


        String[] split = line.split(",");
        if(split.length != 4) {
            throw new InvalidComponentFormatException("Du må bruke ; for å separere datafeltene.");
        }

        // extract all datafields from the string
        String type = split[0];
        String name = split[1];
        String description = split[2];
        double price = parseDouble(split[3], "pris må være et tall");

        //try {
            //double price = doubleStringConverter.stringTilDouble(split[3]);
            return new Komponent(type, name, description, price);

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
