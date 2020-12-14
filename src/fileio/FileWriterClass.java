package fileio;

import java.io.FileWriter;

/**
 * Wrapper peste clasa FileWriter
 * @author alex
 *
 */
public class FileWriterClass {
    protected FileWriter file;

    public FileWriter getOutput() {
        return this.file;
    }
}
