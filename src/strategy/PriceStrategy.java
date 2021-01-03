package strategy;

import java.util.Comparator;
import java.util.List;

import entity.Distributor;
import entity.Producer;

public class PriceStrategy extends EnergyChoiceStrategy {
    
    private class PriceComparator implements Comparator<Producer> {
        @Override
        public int compare(Producer producer1, Producer producer2) {
            if (producer1.getPriceKW().compareTo(producer2.getPriceKW()) == 0) {
                if (producer2.getEnergyPerDistributor().compareTo(producer1.getEnergyPerDistributor()) == 0)
                    return producer1.getId().compareTo(producer2.getId());
                
                return producer2.getEnergyPerDistributor().compareTo(producer1.getEnergyPerDistributor());
            }
            
            return producer1.getPriceKW().compareTo(producer2.getPriceKW());
        }
    }
    
    @Override
    public void assignProducers(List<Producer> producersOnTheMarket, Distributor distributor) {
        selectProducers(producersOnTheMarket, distributor, new PriceComparator());
    }

}
