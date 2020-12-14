import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Consumer;
import entity.Distributor;
import entity.EntityRegister;
import fileio.Input;
import fileio.Writer;

public class Main {

    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(args[0]);
        Input input = objectMapper.readValue(new File(args[0]), Input.class);
        EntityRegister entityRegister = input.getEntityRegister();
        Updater updater = new Updater();
        String mode = "Test";
        Writer writer = new Writer(args[0], mode);
        
        for (Distributor distributor : entityRegister.getDistributors()) {
            int infrastructureCost = distributor.getInfrastructureCost();
            int productionCost = distributor.getProductionCost();
            distributor.changeCosts(infrastructureCost, productionCost, entityRegister);
        }
        
        for (int i = 0; i <= input.getNumberOfTurns(); i++) {
            List<Consumer> consumers = entityRegister.getConsumers();
            List<Distributor> distributors = entityRegister.getDistributors();
            if (mode.equals("StoreComplete")) {
                writer.writeFile(consumers, distributors);
            }
            
            boolean gameStopped = updater.updateConsumers(entityRegister);
            if (gameStopped) {
                break;
            }
            
            updater.updateDistributors(entityRegister);
            if (i == input.getNumberOfTurns())
                break;
            
            updater.addMonthlyUpdate(input);
        }
        
        updater.updateContracts(entityRegister);
        
        writer.writeFile(entityRegister.getConsumers(), entityRegister.getDistributors());
    }
}
