import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Consumer;
import entity.Distributor;
import entity.EntityRegister;
import entity.Producer;
import fileio.Input;
import fileio.Writer;
import strategy.EnergyChoiceStrategy;
import strategy.EnergyChoiceStrategyFactory;
import strategy.EnergyChoiceStrategyType;

public class Main {

    public static void main(final String[] args) throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(args[0]);
        final Input input = objectMapper.readValue(new File(args[0]), Input.class);
        final EntityRegister entityRegister = input.getEntityRegister();
        final Updater updater = new Updater();
        final String mode = "store";
        final Writer writer = new Writer(args[0], mode);

        List<Consumer> consumers = entityRegister.getConsumers();
        List<Distributor> distributors = entityRegister.getDistributors();
        List<Producer> producers = entityRegister.getProducers();
        EnergyChoiceStrategyFactory factory = EnergyChoiceStrategyFactory.getInstance();
        for (final Distributor distributor : entityRegister.getDistributors()) {
            EnergyChoiceStrategyType type = distributor.getProducerStrategy();
            EnergyChoiceStrategy strategy = factory.createEnergyChoiceStrategy(type);
            strategy.assignProducers(producers, distributor);
            distributor.calculatePrice(entityRegister);
        }

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
        }

        updater.updateContracts(entityRegister);

        writer.writeFile(consumers, distributors, producers);
    }
}
