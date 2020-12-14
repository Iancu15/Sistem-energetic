package fileio;

import java.io.File;
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
public class Writer {
    /**
     * Path-ul fisierului de intrare
     */
    private String inputFormat;
    /**
     * Modul de scriere: "Store" pentru a stoca intr-un folder out 
     * rezultatele, StoreComplete pentru a stoca intr-un folder out 
     * rezultatele de la fiecare iteratie, orice altceva pentru a rula
     * checker-ul
     */
    private String mode;

    public Writer(String inputFormat, String mode) throws IOException {
        this.inputFormat = inputFormat;
        this.mode = mode;
    }
    
    /**
     * @return      Un FileWriter la fisierul de iesire
     */
    private FileWriter makeFileWriter() throws IOException {
        if (this.mode.equals("Store")) {
            return this.getOutput(this.inputFormat, false);
        } else if (this.mode.equals("StoreComplete")){
            return this.getOutput(this.inputFormat, true);
        } else {
            return this.getOutput();
        }
    }
    
    /**
     * Scrie listele de consumatori si distributori din parametrii, in format JSON, in fisierul
     * creat in constructor
     * @param consumers     Lista finala de consumatori
     * @param distributors  Lista finala de distributori
     * @throws IOException
     */
    public void writeFile(List<Consumer> consumers, 
                        List<Distributor> distributors) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        Output output = new Output(consumers, distributors);
        
        FileWriter fileWriter = this.makeFileWriter();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, output);
    }
    
    /**
     * Creeaza fisierul de output la path-ul complementar fisierului de citire
     * @param inputFormat   Formatul fisierului de citire
     * @param append        Daca este adevarat se adauga rezultate la final, altfel nu se
     *                      adauga rezultate la final
     * @return              O clasa FileWriter ce pointeaza la fisierul de iesire
     * @throws IOException
     */
    private FileWriter getOutput(String inputFormat, boolean append) throws IOException {
        String outputFormat = inputFormat.replace("in", "out");
        File file = new File(outputFormat);
        
        file.createNewFile();
        return new FileWriter(file, append);
    }

    /**
     * Adauga rezultatele in fisierul result.out pentru testare
     * @return              O clasa FileWriter ce pointeaza la fisierul results.out
     * @throws IOException
     */
    private FileWriter getOutput() throws IOException {
        File file = new File("results.out");
        file.createNewFile();
        return new FileWriter(file, false);
    }
}
