package org.programutvikling.component.io;


import org.programutvikling.component.ComponentRegister;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileSaverTxt implements FileSaver {
    @Override

    //hvordan kan denne save computer uten å måtte omorganisere?
    //hvordan kan man få interfacet til å ta flere typer? component og computer må arve fra samme interface - og det
    // er dette interfacet som tas inn i filesaver interfacen.
    public void save(ComponentRegister reg, Path filePath) throws IOException {
        Files.write(filePath, reg.toString().getBytes());
    }


}