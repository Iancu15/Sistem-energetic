package strategy;

import java.util.Comparator;
import java.util.List;

import entity.Distributor;
import entity.Producer;

public class QuantityStrategy extends EnergyChoiceStrategy {

    // !!!!!!!!!! CAZUL DESCRESCATOR DUPA CANTITATE
    private class QuantityComparator implements Comparator<Producer> {
        @Override
        public int compare(Producer producer1, Producer producer2) {
            if (producer2.getEnergyPerDistributor().compareTo(producer1.getEnergyPerDistributor()) == 0)
                return producer1.getId().compareTo(producer2.getId());
            
            return producer2.getEnergyPerDistributor().compareTo(producer1.getEnergyPerDistributor());
        }
    }

    // !!!!!!!!!!!!! CAZUL IN CARE PLATESTE PT TOTI 100KW
    @Override
    public void assignProducers(List<Producer> producersOnTheMarket, Distributor distributor) {
        selectProducers(producersOnTheMarket, distributor, new QuantityComparator());
    }

}
