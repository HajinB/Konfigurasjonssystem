package org.programutvikling.domain.io;

import org.programutvikling.domain.component.*;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.utility.Item;
import org.programutvikling.domain.utility.NullComponent;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.model.Model;
import org.programutvikling.model.TemporaryComponent;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileOpenerTxt implements FileOpener {

    public void open(Computer computer, Path filePath) throws IOException {
        computer.removeAll();
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if(parseComponent(line) instanceof NullComponent){
                    bufferedReader.readLine(); //leser uten å gjøre noe
                }else{
                    computer.addComponent((Component) parseComponent(line));
                    //temp.getRegister().add(parseComponent(line));
                }
            }
        }
    }
    private Item parseComponent(String line) throws InvalidComponentFormatException {
        String[] split = line.split(";");
        if (split.length != 4) {
            Dialog.showErrorDialog(" Du må bruke ; for å separere datafeltene.");
            throw new InvalidComponentFormatException("Du må bruke ; for å separere datafeltene.");
        }
        String type = split[0];
        String name = split[1];
        String description = split[2];
        double price = parseDouble(split[3], "pris må være et tall");

        Component tempComponent = new Component(type, name, description, price);
        double priceState =
                ComponentValidator.checkPriceAgainstDatabaseGetPrice(tempComponent,
                        Model.INSTANCE.getComponentRegister().getRegister());
        if (priceState > 0) {
            //pris er feil - pricestate har riktig pris.
            TemporaryComponent.INSTANCE.getErrorList().add("prisen på " + tempComponent.getProductName() +" stemmer ikke med databasen, vi " +
                    "endrer den til den riktige prisen");
            return new Component(type, name, description, priceState);
        }
        if (priceState == -2.00) {
            //ingenting stemmer

            TemporaryComponent.INSTANCE.getErrorList().add(tempComponent.getProductName() + " fins ikke i databasen vår, og blir dermed ikke " +
                    "lagt til i listen");
            return new NullComponent();
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
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            String line;
            //hopper over første linje
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                //må man lage en metode som tar bort navn og description fra datamaskinen?
                //første to feltene feks er Navn og pris - så kommer componentregisteret - bør første line være NAVN;

                if(parseComponent(line) instanceof NullComponent){
                    //IKKE LES CURRENT - hvis getproducttype er null, ikke legg til i lista
                    bufferedReader.readLine(); //leser uten å gjøre noe
                }else{
                    list.add(parseComponent(line));
                    System.out.println("open / parse :: "+list);
                    //temp.getRegister().add(parseComponent(line));
                }
            }
        }
        return list;
    }

    public void openWithoutValidation(Computer computer, Path filePath) throws IOException {
        computer.removeAll();
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            String line;
            //hopper over første linje
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                    computer.addComponent((Component) parseComponentWithoutValidation(line));
            }
        }
    }

    private Item parseComponentWithoutValidation(String line) throws InvalidComponentFormatException {
        String[] split = line.split(";");
        if (split.length != 4) {
            throw new InvalidComponentFormatException("Du må bruke ; for å separere datafeltene.");
        }
        String type = split[0];
        String name = split[1];
        String description = split[2];
        double price = parseDouble(split[3], "pris må være et tall");

            return new Component(type, name, description, price);
        }
    }
