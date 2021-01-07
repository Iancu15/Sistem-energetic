package update;

import java.util.List;

import entity.Consumer;

/**
 * Contine actualizarile ce trebuie efectuate intr-o anumita tura
 *
 * @author alex
 *
 */
public final class MonthlyUpdate {
    private List<Consumer> newConsumers;
    private List<CostChange> distributorChanges;
    private List<CostChange> producerChanges;

    public List<Consumer> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<Consumer> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<CostChange> getDistributorChanges() {
        return distributorChanges;
    }

    public void setDistributorChanges(final List<CostChange> distributorChanges) {
        this.distributorChanges = distributorChanges;
    }

    public List<CostChange> getProducerChanges() {
        return producerChanges;
    }

    public void setProduceChanges(final List<CostChange> produceChanges) {
        this.producerChanges = produceChanges;
    }
}
