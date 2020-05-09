package org.programutvikling.domain.utility;

import org.programutvikling.domain.io.FileOpener;
import org.programutvikling.domain.io.FileOpenerJobj;
import org.programutvikling.domain.io.FileOpenerTxt;

public class OpenerFactory {

     public FileOpener createOpener(String s) {
         if (s.equals(".jobj")) {
             return new FileOpenerJobj();
         } else {
             return new FileOpenerTxt();
         }
     }

}
