package entity;

import java.util.List;

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
        long price = 0;
        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                price = distributor.getPrice();
            }
        }
        
        Distributor cheapestDistributor = null;
        // parcurg invers pentru a oferi prioritate celor cu id mai mic
        for (int i = this.distributors.size() - 1; i >= 0; i--) {
            Distributor distributor = this.distributors.get(i);
            if (distributor.getPrice() <= price && !distributor.isBankrupt()) {
                cheapestDistributor = distributor;
                price = distributor.getPrice();
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
