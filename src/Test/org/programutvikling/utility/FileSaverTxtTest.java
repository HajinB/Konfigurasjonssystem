package org.programutvikling.utility;

import org.junit.jupiter.api.Test;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.io.FileOpenerTxt;
import org.programutvikling.domain.io.FileSaverTxt;
import org.programutvikling.domain.utility.ComputerFactory;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileSaverTxtTest {
    @Test
    void save() throws IOException {
        Component componentTest = new Component("MINNE", "Intel Core i9-9900K", "Socket-LGA1151, 8-Core, 16-Thread, 3" +
                ".60/5" +
                ".0GHz, Coffee Lake Refresh, uten kjøler", 6999.99);
        Component componentTest2 = new Component("SSD", "SSD", "blasfbldblfs 8-Core, 16-Thread, 3 .0GHz, Coffee Lake Refresh, uten kjøler", 5999.99);


        ComponentRegister componentRegister = new ComponentRegister();
        componentRegister.getRegister().add(componentTest);
        ComputerFactory computerFactory = new ComputerFactory();
        Computer computer = computerFactory.computerFactory(componentRegister, "test");
        FileSaverTxt fileSaverTxt = new FileSaverTxt();

        // fileSaverTxt.save(computer, Paths.get("org/programutvikling/files/test.txt"));


        //assertTrue(actualMessage.contains("java.nio.file.NoSuchFileException: FileDirectory/Admin/test.txt"));
    }

    @Test
    void open() throws IOException {
        Computer computer = new Computer("test");
        FileOpenerTxt fileOpenerTxt = new FileOpenerTxt();
        Exception exception;
        FileSaverTxt fileSaverTxt = new FileSaverTxt();

        exception = assertThrows(NoSuchFileException.class, () -> {
            fileSaverTxt.save(computer, Paths.get("org/programutvikling/:::()/&%$/#)&/!:;.txt"));

        });
        System.out.println(Arrays.toString(exception.getStackTrace()));

        Component componentTest = new Component("MINNE", "Intel Core i9-9900K", "Socket-LGA1151, 8-Core, 16-Thread, 3" +
                ".60/5" +
                ".0GHz, Coffee Lake Refresh, uten kjøler", 6999.99);
        Component componentTest2 = new Component("SSD", "SSD", "blasfbldblfs 8-Core, 16-Thread, 3" +
                ".60/5" +
                ".0GHz, Coffee Lake Refresh, uten kjøler", 5999.99);

        Computer computer2 = new Computer("test");

        computer2.getComponentRegister().getRegister().add(componentTest);
        computer2.getComponentRegister().getRegister().add(componentTest2);

        // fileSaverTxt.save(computer, Paths.get("org/programutvikling/files/test.txt"));

        Computer computerIn = new Computer("test");

        //fileOpenerTxt.open(computerIn, Paths.get("org/programutvikling/files/test.txt"));
        assertFalse(computerIn.getComponentRegister().getRegister().size() > 0);
        //assertSame(computerIn, computer2);
        exception.getCause();
    }
}
