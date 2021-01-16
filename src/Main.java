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
        final Input input = objectMapper.readValue(new File(args[0]), Input.class);
        final EntityRegister entityRegister = input.getEntityRegister();
        final Updater updater = new Updater();
        final String mode = "test";
        final Writer writer = new Writer(args[0], mode);

        // distribuitorii isi aleg producatorii in prima runda
        final List<Distributor> distributors = entityRegister.getDistributors();
        for (final Distributor distributor : distributors) {
            entityRegister.assignProducersToDistributor(distributor);
        }

        final List<Consumer> consumers = entityRegister.getConsumers();
        final List<Producer> producers = entityRegister.getProducers();
        for (int i = 0; i <= input.getNumberOfTurns(); i++) {
            // in cazul in care e pe modul StoreComplete afisez starea jocului de la fiecare tura
            if (mode.toLowerCase().equals("storecomplete")) {
                writer.writeFile(consumers, distributors, producers);
            }

            // calculeaza preturile pentru a avea oferte pentru consumatori
            for (final Distributor distributor : distributors) {
                distributor.calculatePrice(entityRegister);
            }

            final boolean gameStopped = updater.updateConsumers(entityRegister);
            if (gameStopped) {
                break;
            }

            updater.updateDistributors(entityRegister);

            if (i > 0) {
                // in runda initiala se asigneaza distribuitorii la inceput
                for (final Distributor distributor : distributors) {
                    if (distributor.needsToChangeProducer()) {
                        entityRegister.assignProducersToDistributor(distributor);
                    }
                }

                // prima runda nu se ia in considerare in monthlyStats
                updater.updateProducers(entityRegister, i);
            }

            // ultima runda nu are actualizari pentru final de luna asa ca il pun inainte
            // de addMonthlyUpdate
            if (i == input.getNumberOfTurns()) {
                break;
            }

            updater.addMonthlyUpdate(input);
        }

        updater.updateContracts(entityRegister);
        // reordonez lista de producatori dupa id pentru scriere
        entityRegister.getProducers().sort((u, v) -> u.getId() - v.getId());

        writer.writeFile(consumers, distributors, producers);
    }
}
