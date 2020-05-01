package org.programutvikling.domain.component.io;

public class SaverFactory {

        public FileSaver createSaver(String s) {
            if (s.equals(".jobj")) {
                return new FileSaverJobj();
            } else {
                return new FileSaverTxt();
            }
        }

}
