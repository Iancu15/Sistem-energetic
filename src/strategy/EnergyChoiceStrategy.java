package strategy;

import java.util.Comparator;
import java.util.List;

import entity.Distributor;
import entity.MonthlyStat;
import entity.Producer;

public abstract class EnergyChoiceStrategy {

    public abstract void assignProducers(List<Producer> producersOnTheMarket, Distributor distributor);
    
    @SuppressWarnings("deprecation")
    protected void selectProducers(List<Producer> producersOnTheMarket, Distributor distributor, 
                                                                Comparator<Producer> comparator) {
        producersOnTheMarket.sort(comparator);
        int energyNeeded = distributor.getEnergyNeededKW();
        int cost = 0;
        for (Producer producer : producersOnTheMarket) {
            MonthlyStat monthlyStat = producer.getMonthlyStats().getLast();
            if (monthlyStat.getDistributorsIds().size() >= producer.getMaxDistributors()) {
                continue;
            }

            monthlyStat.getDistributorsIds().add(distributor.getId());
            producer.addObserver(distributor);
            distributor.setNeedsToChangeProducer(false);
            
            energyNeeded -= producer.getEnergyPerDistributor();
            cost += producer.getEnergyPerDistributor() * producer.getPriceKW();
            if (energyNeeded <= 0) {
                break;
            }
        }

        long productionCost = Math.round(Math.floor(cost / 10));
        distributor.setProductionCost(productionCost);
    }
}
