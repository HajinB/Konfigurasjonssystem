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
    public void open(Computer computer, Path filePath) throws IOException {
        computer.removeAll();
        // try-with-resources lukker automatisk filen

        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                computer.addComponent(parseComponent(line));
            }
        }
    }


    //(String type, String name, String description, double price)
    private Component parseComponent(String line) throws InvalidComponentFormatException {


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
            return new Computer(name, description, price);

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
