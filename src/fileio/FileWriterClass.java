package fileio;

import java.io.FileWriter;

/**
 * Wrapper peste clasa FileWriter
 *
 * @author alex
 *
 */
public class FileWriterClass {
    protected FileWriter file;

    /**
     * @return      instanta de FileWriter aferenta wrapper-ului
     */
    public FileWriter getOutput() {
        return this.file;
    }
}
