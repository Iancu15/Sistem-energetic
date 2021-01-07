package fileio;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Consumer;
import entity.Distributor;
import entity.Producer;

/**
 * Ajuta in scrierea registrului de consumatori si distributori, in format JSON,
 * intr-un fisier de iesire
 *
 * @author alex
 *
 */
public final class Writer {
    /**
     * Formatul path-ului fisierului de citire
     */
    private final String inputFormat;
    /**
     * Modul de scriere: Store, StoreComplete sau Test
     */
    private final String mode;

    public Writer(final String inputFormat, final String mode) throws IOException {
        this.inputFormat = inputFormat;
        this.mode = mode;
    }

    /**
     * Scrie listele de consumatori si distributori din parametrii, in format JSON,
     * in fisierul creat in constructor
     *
     * @param consumers    Lista finala de consumatori
     * @param distributors Lista finala de distributori
     * @throws IOException
     */
    public void writeFile(final List<Consumer> consumers, final List<Distributor> distributors,
            final List<Producer> producers) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Output output = new Output(consumers, distributors, producers);

        final FileWriterFactory factory = FileWriterFactory.getInstance();
        final FileWriter file = factory.createFileWriter(this.mode, this.inputFormat).getOutput();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, output);
    }
}
