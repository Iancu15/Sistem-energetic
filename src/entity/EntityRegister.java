package entity;

import java.util.List;

/**
 * Registrul ce tine cont de toate entitatile ce au participat in joc
 * @author alex
 *
 */
public final class EntityRegister {
    private List<Consumer> consumers;
    private List<Distributor> distributors;

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<Consumer> consumers) {
        this.consumers = consumers;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<Distributor> distributors) {
        this.distributors = distributors;
    }

    /**
     * Cauta distributorul cu cea mai ieftina rata
     * @return  Cel mai ieftin distributor
     */
    public Distributor getCheapestDistributor() {
        long price = 0;
        for (final Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                price = distributor.getPrice();
            }
        }

        Distributor cheapestDistributor = null;
        // parcurg invers pentru a oferi prioritate celor cu id mai mic
        for (int i = this.distributors.size() - 1; i >= 0; i--) {
            final Distributor distributor = this.distributors.get(i);
            if (distributor.getPrice() <= price && !distributor.isBankrupt()) {
                cheapestDistributor = distributor;
                price = distributor.getPrice();
            }
        }

        return cheapestDistributor;
    }

    public Consumer findConsumer(final Integer consumerId) {
        for (final Consumer consumer : this.consumers) {
            if (consumer.getId().equals(consumerId)) {
                return consumer;
            }
        }

        return null;
    }
}
