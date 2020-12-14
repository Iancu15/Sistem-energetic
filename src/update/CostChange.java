package update;

import entity.Distributor;
import entity.EntityRegister;

/**
 * Contine modificarile de costuri ale unui distributor la finalul unei anumite ture
 * @author alex
 *
 */
public final class CostChange {
    private Integer id;
    private int infrastructureCost;
    private int productionCost;

    public Integer getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public Integer getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final Integer infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public Integer getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final Integer productionCost) {
        this.productionCost = productionCost;
    }

    /**
     * Actualizeaza costurile distributorului cu id-ul aferent instantei
     * @param entityRegister
     */
    public void updateDistributor(final EntityRegister entityRegister) {
        for (final Distributor distributor : entityRegister.getDistributors()) {
            if (distributor.getId().equals(this.id)) {
                distributor.changeCosts(this.infrastructureCost, this.productionCost, entityRegister);
                break;
            }
        }
    }
}
