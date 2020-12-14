package fileio;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Consumer;
import entity.Distributor;

/**
 * Ajuta in scrierea registrului de consumatori si distributori, in format JSON, intr-un fisier
 * de iesire
 * @author alex
 *
 */
public final class Writer {
    private final FileWriter file;

    public Writer(final String inputFormat, final String mode) throws IOException {
        final FileWriterFactory factory = FileWriterFactory.getInstance();
        this.file = factory.createFileWriter(mode, inputFormat).getOutput();
    }

    /**
     * Scrie listele de consumatori si distributori din parametrii, in format JSON, in fisierul
     * creat in constructor
     * @param consumers     Lista finala de consumatori
     * @param distributors  Lista finala de distributori
     * @throws IOException
     */
    public void writeFile(final List<Consumer> consumers,
                        final List<Distributor> distributors) throws IOException{
        final ObjectMapper objectMapper = new ObjectMapper();
        final Output output = new Output(consumers, distributors);

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(this.file, output);
    }
}
