package strategy;

import java.util.Comparator;
import java.util.List;

import entity.Distributor;
import entity.Producer;

public class GreenStrategy extends EnergyChoiceStrategy {
    
    private class GreenComparator implements Comparator<Producer> {
        @Override
        public int compare(Producer producer1, Producer producer2) {
            if (producer1.getEnergyType().isRenewable() == producer2.getEnergyType().isRenewable()) {
                if (producer1.getPriceKW().compareTo(producer2.getPriceKW()) == 0) {
                    if (producer2.getEnergyPerDistributor().compareTo(producer1.getEnergyPerDistributor()) == 0)
                        return producer1.getId().compareTo(producer2.getId());
                    
                    return producer2.getEnergyPerDistributor().compareTo(producer1.getEnergyPerDistributor());
                }
                
                return producer1.getPriceKW().compareTo(producer2.getPriceKW());
            }
            
            // daca ajunge aici inseamna ca au valori diferite, iar daca producatorul 1 are
            // energie regenerabila, atunci producatorul 2 nu va avea
            if (producer1.getEnergyType().isRenewable()) {
                return -1;
            }
            
            return 1;
        }
    }

    @Override
    public void assignProducers(List<Producer> producersOnTheMarket, Distributor distributor) {
        selectProducers(producersOnTheMarket, distributor, new GreenComparator());
    }

}
