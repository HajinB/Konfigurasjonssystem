
import org.junit.jupiter.api.Test;
import org.programutvikling.komponent.Komponent;
import org.programutvikling.komponent.KomponentRegister;
import org.programutvikling.komponent.io.FileSaverJobj;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileSaverTxtTest {

    @Test
    void save() {

    }


    @Test
    public void test() throws IOException {
        Komponent komponentTest = new Komponent("CPU","Intel Core i9-9900K", "Socket-LGA1151, 8-Core, 16-Thread, 3" +
                ".60/5" +
                ".0GHz, Coffee Lake Refresh, uten kjøler\n", 6999.99);
        KomponentRegister komponentRegister = new KomponentRegister();
        komponentRegister.addKomponent(komponentTest);

        String contents = komponentTest.toString();

        File path = new File("Directory");
        FileSaverJobj saver = new FileSaverJobj();
        // call teh metod
        saver.save(komponentRegister, Paths.get(path.getPath()));

        // tacke a reference to the file
        File file = new File(String.valueOf(path));

        // I assert that the file is not empty
        assertTrue(file.length() > 0);

        // I assert that the file content is the same of the contents variable
        //assertSame(
    }
}

/*
    static void saveFile(String contents, String path) throws IOException {

        File file = new File(path);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

        pw.println(contents);
    }*/
