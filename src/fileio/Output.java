package fileio;

import java.util.List;

import entity.Consumer;
import entity.Distributor;

public class Output {
    private List<Consumer> consumers;
    private List<Distributor> distributors;
    
    public Output(List<Consumer> consumers, List<Distributor> distributors) {
        this.consumers = consumers;
        this.distributors = distributors;
    }

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
}
