package strategy;

import java.util.Comparator;
import java.util.List;

import entity.Distributor;
import entity.Producer;

public class PriceStrategy extends EnergyChoiceStrategy {

    /**
     * Comparatorul ce sorteaza producatorul dupa pret, apoi dupa cantitate si apoi dupa id
     * @author alex
     *
     */
    private class PriceComparator implements Comparator<Producer> {
        @Override
        public int compare(final Producer producer1, final Producer producer2) {
            if (producer1.getPriceKW().compareTo(producer2.getPriceKW()) == 0) {
                if (producer2.getEnergyPerDistributor().compareTo(producer1.getEnergyPerDistributor()) == 0) {
                    return producer1.getId().compareTo(producer2.getId());
                }

                return producer2.getEnergyPerDistributor().compareTo(producer1.getEnergyPerDistributor());
            }

            return producer1.getPriceKW().compareTo(producer2.getPriceKW());
        }
    }

    /**
     * Asigneaza primii producatori, din lista sortata dupa pret, distribuitorului
     */
    @Override
    public void assignProducers(final List<Producer> producersOnTheMarket,
                                                                final Distributor distributor) {
        selectProducers(producersOnTheMarket, distributor, new PriceComparator());
    }

}
