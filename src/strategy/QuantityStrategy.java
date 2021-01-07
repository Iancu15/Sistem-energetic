package strategy;

import java.util.Comparator;
import java.util.List;

import entity.Distributor;
import entity.Producer;

public class QuantityStrategy extends EnergyChoiceStrategy {

    // !!!!!!!!!! CAZUL DESCRESCATOR DUPA CANTITATE
    /**
     * Compara producatorii dupa cantitate in sens descrescator, apoi dupa id
     * @author alex
     *
     */
    private class QuantityComparator implements Comparator<Producer> {
        @Override
        public int compare(final Producer producer1, final Producer producer2) {
            if (producer2.getEnergyPerDistributor().compareTo(producer1.getEnergyPerDistributor()) == 0) {
                return producer1.getId().compareTo(producer2.getId());
            }

            return producer2.getEnergyPerDistributor().compareTo(producer1.getEnergyPerDistributor());
        }
    }

    /**
     * Asigneaza primii producatori, din lista sortata dupa cantitate, distribuitorului
     */
    @Override
    public void assignProducers(final List<Producer> producersOnTheMarket,
                                                                final Distributor distributor) {
        selectProducers(producersOnTheMarket, distributor, new QuantityComparator());
    }

}
