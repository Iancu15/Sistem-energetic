package strategy;

import java.util.Comparator;
import java.util.List;

import entity.Distributor;
import entity.Producer;

public abstract class EnergyChoiceStrategy {

    public abstract void assignProducers(List<Producer> producersOnTheMarket, Distributor distributor);
    
    @SuppressWarnings("deprecation")
    protected void selectProducers(List<Producer> producersOnTheMarket, Distributor distributor, 
                                                                Comparator<Producer> comparator) {
        for (Producer producer : producersOnTheMarket) {
            producer.getDistributorsIds().remove(distributor.getId());
        }

        producersOnTheMarket.sort(comparator);
        int energyNeeded = distributor.getEnergyNeededKW();
        int cost = 0;
        for (Producer producer : producersOnTheMarket) {
            if (producer.getDistributorsIds().size() >= producer.getMaxDistributors()) {
                continue;
            }

            producer.getDistributorsIds().add(distributor.getId());
            producer.addObserver(distributor);

            energyNeeded -= producer.getEnergyPerDistributor();
            cost += producer.getEnergyPerDistributor() * producer.getPriceKW();
            if (energyNeeded <= 0) {
                break;
            }
        }

        long productionCost = Math.round(Math.floor(cost / 10));
        distributor.setProductionCost(productionCost);
        distributor.setNeedsToChangeProducer(false);
    }
}
