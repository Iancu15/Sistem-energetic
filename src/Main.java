import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Consumer;
import entity.Distributor;
import entity.EntityRegister;
import entity.Producer;
import fileio.Input;
import fileio.Writer;

public class Main {

    public static void main(final String[] args) throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(args[0]);
        final Input input = objectMapper.readValue(new File(args[0]), Input.class);
        final EntityRegister entityRegister = input.getEntityRegister();
        final Updater updater = new Updater();
        final String mode = "test";
        final Writer writer = new Writer(args[0], mode);

        List<Distributor> distributors = entityRegister.getDistributors();
        for (final Distributor distributor : distributors) {
            entityRegister.assignProducersToDistributor(distributor);
        }

        List<Consumer> consumers = entityRegister.getConsumers();
        List<Producer> producers = entityRegister.getProducers();
        for (int i = 0; i <= input.getNumberOfTurns(); i++) {
            // in cazul in care e pe modul StoreComplete afisez starea jocului de la fiecare tura
            if (mode.toLowerCase().equals("storecomplete")) {
                writer.writeFile(consumers, distributors, producers);
            }

            final boolean gameStopped = updater.updateConsumers(entityRegister);
            if (gameStopped) {
                break;
            }

            updater.updateDistributors(entityRegister);
            if (i == input.getNumberOfTurns())
                break;
            
            updater.addMonthlyUpdate(input);
            
            for (Distributor distributor : entityRegister.getDistributors()) {
                if (distributor.needsToChangeProducer()) {
                    entityRegister.assignProducersToDistributor(distributor);
                }
            }
            
            updater.updateProducers(entityRegister, i);
        }

        updater.updateContracts(entityRegister);
        // reordonez lista de producatori dupa id pentru scriere
        entityRegister.getProducers().sort((u, v) -> u.getId() - v.getId());

        writer.writeFile(consumers, distributors, producers);
    }
}
