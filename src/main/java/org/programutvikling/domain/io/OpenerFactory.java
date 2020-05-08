package org.programutvikling.domain.io;

public class OpenerFactory {

     public FileOpener createOpener(String s) {
         if (s.equals(".jobj")) {
             return new FileOpenerJobj();
         } else {
             return new FileOpenerTxt();
         }
     }

}
