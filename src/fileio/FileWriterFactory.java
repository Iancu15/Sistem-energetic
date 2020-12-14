package fileio;

import java.io.IOException;

/**
 * Fabrica singleton de FileWriterClass
 * @author alex
 *
 */
public final class FileWriterFactory {
    private static FileWriterFactory instance = null;

    private FileWriterFactory() {
    }

    public static FileWriterFactory getInstance() {
        if (instance == null) {
            instance = new FileWriterFactory();
        }

        return instance;
    }

    /**
     * Creeaza o clasa FileWriterClass in functie de modul ales si formatul fisierului de intrare
     * primit
     * @param mode
     * @param inputFormat
     * @return
     * @throws IOException
     */
    public FileWriterClass createFileWriter(final String mode, final String inputFormat) throws IOException {
        switch(mode.toLowerCase()) {
            case "store": return new FileWriterClassStore(inputFormat);
            case "storecomplete": return new FileWriterClassStoreComplete(inputFormat);
            case "test": return new FileWriterClassTest();
        }

        return null;
    }
}
