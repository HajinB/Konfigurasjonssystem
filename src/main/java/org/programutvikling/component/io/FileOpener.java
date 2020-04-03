package org.programutvikling.component.io;

import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.computer.Computer;

import java.io.IOException;
import java.nio.file.Path;

//det vi vil åpne, sier oppgaven er hele registeret (som å laste inn filer fra start for sluttbruker)




public interface FileOpener {

    //interfacet vil kanskje ikke fungere fordi vi vil ha både muligheten til å åpne enkelt-komponenter og lister og
    // ferdig konfigurerte?
    void open(ComponentRegister componentRegister, Path filePath) throws IOException;

    void open(Computer computer, Path filePath) throws IOException;

}