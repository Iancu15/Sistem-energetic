package fileio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Fiu al clasei FileWriterClass ce contine FileWriter-ul care testeaza
 *
 * @author alex
 *
 */
public class FileWriterClassTest extends FileWriterClass {
    public FileWriterClassTest() throws IOException {
        final File file = new File("results.out");
        file.createNewFile();
        this.file = new FileWriter(file, false);
    }
}
