import entity.Consumer;
import entity.Contract;
import entity.Distributor;
import fileio.EntityRegister;
import fileio.Input;
import update.CostChange;
import update.MonthlyUpdate;

public class Updater {
    public void updateConsumers(EntityRegister entityRegister) {
        for (Consumer consumer : entityRegister.getConsumers()) {
            if (consumer.isBankrupt()) {
                if (consumer.getDistributor() != null) {
                    consumer.getDistributor().removeContract(consumer);
                }
                
                continue;
            }
            
            Contract contract;
            if (consumer.getDistributor() != null) {
                contract = consumer.getDistributor().findContract(consumer.getId());
                if (contract.getRemainedContractMonths() == 0 && consumer.getDebt() == 0) {
                    consumer.getDistributor().removeContract(consumer);
                }
            }
            
            if (consumer.getDistributor() == null) {
                Distributor myDistributor = entityRegister.getCheapestDistributor();
                if (myDistributor == null) {
                    break;
                }
                
                myDistributor.addContract(consumer);
            }
            
            consumer.setBudget(consumer.getBudget() + consumer.getMonthlyIncome());
            contract = consumer.getDistributor().findContract(consumer.getId());
            long newBudget = consumer.getBudget() - contract.getPrice() - consumer.getDebt();
            contract.setRemainedContractMonths(contract.getRemainedContractMonths() - 1);
            if (newBudget < 0) {
                if (consumer.getDebt() != 0) {
                    consumer.setBankrupt(true);
                    continue;
                }
                
                consumer.setDebt(Math.round(Math.floor(1.2 * contract.getPrice())));
                contract.setOnHold(true);
            } else {
                if (consumer.getDebt() != 0) {
                    consumer.setDebt(0);
                }
                
                consumer.setBudget(newBudget);
            }
        }
    }
    
    public void updateDistributors(EntityRegister entityRegister) {
        for (Distributor distributor : entityRegister.getDistributors()) {
            if (distributor.isBankrupt()) {
                continue;
            }
            
            distributor.calculateBudget(entityRegister);
            if (distributor.getBudget() <= 0) {
                distributor.setBankrupt(true);
                continue;
            }
            
            distributor.recalculatePrice(entityRegister);
        }
    }
    
    public void addMonthlyUpdate(Input input) {
        MonthlyUpdate update = input.getMonthlyUpdates().get(0);
        EntityRegister entityRegister = input.getEntityRegister();
        for (CostChange costChange : update.getCostsChanges()) {
            costChange.updateDistributor(entityRegister);
        }
        
        for (Consumer consumer : update.getNewConsumers()) {
            entityRegister.getConsumers().add(consumer);
        }
        
        input.getMonthlyUpdates().remove(0);
    }
}
