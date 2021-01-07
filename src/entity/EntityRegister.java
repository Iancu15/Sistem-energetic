package entity;

import java.util.List;

import strategy.EnergyChoiceStrategy;
import strategy.EnergyChoiceStrategyFactory;
import strategy.EnergyChoiceStrategyType;

/**
 * Registrul ce tine cont de toate entitatile ce au participat in joc
 *
 * @author alex
 *
 */
public final class EntityRegister {
    private List<Consumer> consumers;
    private List<Distributor> distributors;
    private List<Producer> producers;

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

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(final List<Producer> producers) {
        this.producers = producers;
    }

    /**
     * Cauta distribuitorul cu cea mai ieftina rata
     *
     * @return Cel mai ieftin distribuitor
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

    /**
     * Cauta entitatea cu id-ul aferent in lista de entitati primita ca parametru
     * @param Id            Id-ul entitatii cautate
     * @param entities      Lista in care se afla entitatea cautata
     * @return              Entitatea cautata
     */
    public Entity findEntity(final Integer id, final List<? extends Entity> entities) {
        for (final Entity entity : entities) {
            if (entity.getId().equals(id)) {
                return entity;
            }
        }

        return null;
    }

    /**
     * Asignez producatorii catre distribuitor in functie de strategia urmata de acesta
     * @param distributor       Distribuitorul care isi alege producatorii
     */
    public void assignProducersToDistributor(final Distributor distributor) {
        final EnergyChoiceStrategyType type = distributor.getProducerStrategy();
        final EnergyChoiceStrategyFactory factory = EnergyChoiceStrategyFactory.getInstance();
        final EnergyChoiceStrategy strategy = factory.createEnergyChoiceStrategy(type);
        strategy.assignProducers(this.producers, distributor);
    }

}
