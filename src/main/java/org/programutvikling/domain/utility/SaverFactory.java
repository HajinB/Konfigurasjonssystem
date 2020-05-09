package org.programutvikling.domain.utility;

import org.programutvikling.domain.io.FileSaver;
import org.programutvikling.domain.io.FileSaverJobj;
import org.programutvikling.domain.io.FileSaverTxt;

public class SaverFactory {

        public FileSaver createSaver(String s) {
            if (s.equals(".jobj")) {
                return new FileSaverJobj();
            } else {
                return new FileSaverTxt();
            }
        }
}
