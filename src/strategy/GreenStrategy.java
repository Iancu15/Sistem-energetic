package strategy;

import java.util.Comparator;
import java.util.List;

import entity.Distributor;
import entity.Producer;

public class GreenStrategy extends EnergyChoiceStrategy {

    /**
     * Comparatorul ce sorteaza intr-un mod "GREEN" producatorii
     * @author alex
     *
     */
    private class GreenComparator implements Comparator<Producer> {
        @Override
        public int compare(final Producer producer1, final Producer producer2) {
            // compara intai dupa tipul de energie oferita, daca e acelasi va compara dupa pretul
            if (producer1.getEnergyType().isRenewable() == producer2.getEnergyType().isRenewable()) {
                // daca preturile sunt egale compara dupa cantitate
                if (producer1.getPriceKW().compareTo(producer2.getPriceKW()) == 0) {
                    // daca cantitatea e egala compara dupa id
                    if (producer2.getEnergyPerDistributor().compareTo(producer1.getEnergyPerDistributor()) == 0) {
                        return producer1.getId().compareTo(producer2.getId());
                    }

                    // daca cantitatea nu e egala returneaza diferenta (sens descrescator)
                    return producer2.getEnergyPerDistributor().compareTo(producer1.getEnergyPerDistributor());
                }

                // daca preturile nu sunt egale returneaza diferenta
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

    /**
     * Asigneaza producatori dupa ce ii sorteaza intr-un mod "GREEN" prin intermediul
     * comparatorului GreenComparator
     */
    @Override
    public void assignProducers(final List<Producer> producersOnTheMarket,
                                                                final Distributor distributor) {
        selectProducers(producersOnTheMarket, distributor, new GreenComparator());
    }

}
