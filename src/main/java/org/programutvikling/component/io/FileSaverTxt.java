package org.programutvikling.component.io;


import org.programutvikling.computer.Computer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class FileSaverTxt implements FileSaver {

    //hvordan kan denne save computer uten å måtte omorganisere?
    //hvordan kan man få interfacet til å ta flere typer? component og computer må arve fra samme interface - og det
    // er dette interfacet som tas inn i filesaver interfacen.
    public void save(Computer computer, Path filePath) throws IOException {
        Files.write(filePath, computer.toString().getBytes());
    }


    @Override
    public void save(ArrayList<Object> componentRegister, Path filePath) throws IOException {

    }
}