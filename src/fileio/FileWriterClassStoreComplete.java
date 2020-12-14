package fileio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Fiu al clasei FileWriterClass ce contine FileWriter-ul care stocheaza fisierele de output in
 * forma completa(toate iteratiile jocului)
 * @author alex
 *
 */
public class FileWriterClassStoreComplete extends FileWriterClass {
    public FileWriterClassStoreComplete(final String inputFormat) throws IOException {
        final String outputFormat = inputFormat.replace("in", "out");
        final File file = new File(outputFormat);
        file.createNewFile();
        this.file = new FileWriter(file, true);
    }
}
