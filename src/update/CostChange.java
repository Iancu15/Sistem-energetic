package update;

import com.fasterxml.jackson.annotation.JsonAlias;

import entity.Distributor;
import entity.Producer;

/**
 * Contine modificarile de costuri ale unui distribuitor la finalul unei anumite
 * ture si (in cazul nostru) schimbarile de cantitati oferite de producatori
 *
 * @author alex
 *
 */
public final class CostChange {
    private Integer id;
    private int valueChange;

    public Integer getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    @JsonAlias({ "infrastructureCost", "energyPerDistributor" })
    public int getValueChange() {
        return valueChange;
    }

    @JsonAlias({ "infrastructureCost", "energyPerDistributor" })
    public void setValueChange(final int valueChange) {
        this.valueChange = valueChange;
    }

    /**
     * Actualizeaza costul de productie al distribuitorului primit ca parametru
     * @param distributor       Distribuitorul asupra caruia se fac actualizari
     */
    public void updateDistributor(final Distributor distributor) {
        distributor.setInfrastructureCost(this.valueChange);
    }

    /**
     * Actualizeaza cantitatea de energie al producatorului dat ca parametru si ii atentioneaza
     * distribuitorii abonati legat de aceasta schimbare
     * @param producer          Producatorul asupra caruia se fac actualizari
     */
    public void updateProducer(final Producer producer) {
        producer.setEnergyPerDistributor(this.valueChange);
        producer.notifyDistributors();
    }
}
