package org.programutvikling.komponent.io;

import org.programutvikling.komponent.KomponentRegister;

import java.io.IOException;
import java.nio.file.Path;

//det vi vil åpne, sier oppgaven er hele registeret (som å laste inn filer fra start for sluttbruker)




public interface FileOpener {

    //interfacet vil kanskje ikke fungere fordi vi vil ha både muligheten til å åpne enkelt-komponenter og lister og
    // ferdig konfigurerte?
    void open(KomponentRegister komponentRegister, Path filePath) throws IOException;

}
