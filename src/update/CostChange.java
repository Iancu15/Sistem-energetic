package update;

import com.fasterxml.jackson.annotation.JsonAlias;

import entity.Distributor;
import entity.EntityRegister;
import entity.Producer;

/**
 * Contine modificarile de costuri ale unui distributor la finalul unei anumite ture
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
    
    @JsonAlias({"infrastructureCost", "energyPerDistributor"})
    public int getValueChange() {
        return valueChange;
    }
    
    @JsonAlias({"infrastructureCost", "energyPerDistributor"})
    public void setValueChange(int valueChange) {
        this.valueChange = valueChange;
    }
    
    public void updateDistributor(Distributor distributor) {
        distributor.setInfrastructureCost(this.valueChange);
    }
    
    public void updateProducer(Producer producer) {
        producer.setEnergyPerDistributor(this.valueChange);
    }
}
