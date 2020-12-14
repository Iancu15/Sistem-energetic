package fileio;

import java.util.List;

import entity.Consumer;
import entity.Distributor;

public class EntityRegister {
    private List<Consumer> consumers;
    private List<Distributor> distributors;
    
    public List<Consumer> getConsumers() {
        return consumers;
    }
    
    public void setConsumers(List<Consumer> consumers) {
        this.consumers = consumers;
    }
    
    public List<Distributor> getDistributors() {
        return distributors;
    }
    
    public void setDistributors(List<Distributor> distributors) {
        this.distributors = distributors;
    }
    
    public Distributor getCheapestDistributor() {
        long price = this.distributors.get(this.distributors.size() - 1).getPrice();
        Distributor cheapestDistributor = null;
        // parcurg invers pentru a oferi prioritate celor cu id mai mic
        for (int i = this.distributors.size() - 1; i >= 0; i--) {
            Distributor distributor = this.distributors.get(i);
            if (distributor.getPrice() <= price && !distributor.isBankrupt()) {
                cheapestDistributor = distributor;
            }
        }
        
        return cheapestDistributor;
    }
    
    public Consumer findConsumer(Integer consumerId) {
        for (Consumer consumer : this.consumers) {
            if (consumer.getId().equals(consumerId)) {
                return consumer;
            }
        }
        
        return null;
    }
}
