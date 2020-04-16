package org.programutvikling.component.io;

public class SaverFactory {

        public FileSaver createOpener(String s) {
            if (s.equals(".jobj")) {
                return new FileSaverJobj();
            } else {
                return new FileSaverTxt();
            }
        }

}
