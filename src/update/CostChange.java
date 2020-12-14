package update;

import entity.Distributor;
import fileio.EntityRegister;

public class CostChange {
    private Integer id;
    private int infrastructureCost;
    private int productionCost;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Integer getInfrastructureCost() {
        return infrastructureCost;
    }
    
    public void setInfrastructureCost(Integer infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }
    
    public Integer getProductionCost() {
        return productionCost;
    }
    
    public void setProductionCost(Integer productionCost) {
        this.productionCost = productionCost;
    }

    public void updateDistributor(EntityRegister entityRegister) {
        for (Distributor distributor : entityRegister.getDistributors()) {
            if (distributor.getId().equals(this.id)) {
                distributor.changeCosts(this.infrastructureCost, this.productionCost, entityRegister);
                break;
            }
        }
    }
}
