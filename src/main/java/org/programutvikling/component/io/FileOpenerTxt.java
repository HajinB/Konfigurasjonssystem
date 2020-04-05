package org.programutvikling.component.io;

import org.programutvikling.computer.Computer;
import org.programutvikling.gui.Converter;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.io.FileOpener;



import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileOpenerTxt implements FileOpener {

    @Override
    public void open(ComponentRegister register, Path filePath) throws IOException {
        register.removeAll();
        // try-with-resources lukker automatisk filen
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                register.addComponent(parseComponent(line));
            }
        }
    }

    @Override
    public void open(Computer computer, Path filePath) throws IOException {

    }

    //(String type, String name, String description, double price)
    private Component parseComponent(String line) throws InvalidComponentFormatException {


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
