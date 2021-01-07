package strategy;

import java.util.Comparator;

import java.util.List;

import entity.Distributor;
import entity.Producer;

/**
 * Clasa abstracta mostenita de strategii
 * @author alex
 *
 */
public abstract class EnergyChoiceStrategy {

    /**
     * Asigneaza producatorii in modul specific strategiei
     * @param producersOnTheMarket      Producatorii de pe market
     * @param distributor               Distribuitorul care isi alege producatorii
     */
    public abstract void assignProducers(List<Producer> producersOnTheMarket,
                                                                        Distributor distributor);

    @SuppressWarnings("deprecation")
    /**
     * Alege producatori din lista sortata pana este satisfacut pragul de energie al
     * distribuitorului
     * @param producersOnTheMarket      Producatorii de pe piata
     * @param distributor               Distribuitorul care isi alege producatorii
     * @param comparator                Comparator ce ordoneaza producatorii in functie de
     *                                  strategia aleasa de distribuitor
     */
    protected void selectProducers(final List<Producer> producersOnTheMarket,
                        final Distributor distributor, final Comparator<Producer> comparator) {
        // reziliez toate contractele cu producatorii
        for (final Producer producer : producersOnTheMarket) {
            producer.getDistributorsIds().remove(distributor.getId());
        }

        // sortez producatorii in functie de comparatorul specific strategiei alese
        producersOnTheMarket.sort(comparator);
        int energyNeeded = distributor.getEnergyNeededKW();
        int cost = 0;
        for (final Producer producer : producersOnTheMarket) {
            // sar peste producatorii care deja si-au atins limita de distribuitori
            if (producer.getDistributorsIds().size() >= producer.getMaxDistributors()) {
                continue;
            }

            // adaug distribuitorul in lista de distribuitori a producatorului si il adaug ca
            // observator aceluiasi producator
            producer.getDistributorsIds().add(distributor.getId());
            producer.addObserver(distributor);

            // scad energia oferita de producator din energia totala necesara si cresc costul
            // de productie in functie de cantitatea si pretul producatorului
            energyNeeded -= producer.getEnergyPerDistributor();
            cost += producer.getEnergyPerDistributor() * producer.getPriceKW();

            // in momentul in care s-a satisfacut pragul de energie necesara al distribuitorului
            // ies din loop si nu mai caut alti producatori
            if (energyNeeded <= 0) {
                break;
            }
        }

        // calculez costul de productie si il setez distribuitorului
        final long productionCost = Math.round(Math.floor(cost / 10));
        distributor.setProductionCost(productionCost);

        // cum distribuitorul si-a gasit producator, atunci ii zic sa nu mai caute pentru moment
        distributor.setNeedsToChangeProducer(false);
    }
}
