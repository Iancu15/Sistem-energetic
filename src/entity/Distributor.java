package entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import fileio.EntityRegister;

@JsonPropertyOrder({"id", "budget", "isBankrupt", "contracts"})
public class Distributor {
    private Integer id;
    private int contractLength;
    private long budget;
    private int infrastructureCost;
    private int productionCost;
    private long price;
    private List<Contract> contracts = new ArrayList<Contract>();
    private boolean isBankrupt = false;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @JsonIgnore
    public int getContractLength() {
        return contractLength;
    }
    
    @JsonProperty
    public void setContractLength(int contractLength) {
        this.contractLength = contractLength;
    }
    
    @JsonAlias("initialBudget")
    public long getBudget() {
        return budget;
    }
    
    @JsonAlias("initialBudget")
    public void setBudget(int budget) {
        this.budget = budget;
    }
    
    @JsonIgnore
    @JsonAlias("initialInfrastructureCost")
    public int getInfrastructureCost() {
        return infrastructureCost;
    }
    
    @JsonProperty
    @JsonAlias("initialInfrastructureCost")
    public void setInfrastructureCost(int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }
    
    @JsonIgnore
    @JsonAlias("initialProductionCost")
    public int getProductionCost() {
        return productionCost;
    }
    
    @JsonProperty
    @JsonAlias("initialProductionCost")
    public void setProductionCost(int productionCost) {
        this.productionCost = productionCost;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
    
    @JsonProperty("isBankrupt")
    public boolean isBankrupt() {
        return isBankrupt;
    }
    
    @JsonProperty("isBankrupt")
    public void setBankrupt(boolean isBankrupt) {
        this.isBankrupt = isBankrupt;
    }
    
    @JsonIgnore
    public long getPrice() {
        return price;
    }

    public void changeCosts(int infrastructureCost, int productionCost, EntityRegister entityRegister) {
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
        long profit = Math.round(Math.floor(0.2 * productionCost));
        if (this.contracts.isEmpty()) {
            this.price = infrastructureCost + productionCost + profit;
            return;
        }
        
        int numberOfConsumers = 0;
        for (Contract contract : this.contracts) {
            if (contract.isOnHold()) {
                if (entityRegister.findConsumer(contract.getConsumerId()).isBankrupt()) {
                    continue;
                }
            }
            
            numberOfConsumers++;
        }
        
        double currentInfrastructureCost;
        if (numberOfConsumers != 0) {
            currentInfrastructureCost = Math.floor(infrastructureCost / numberOfConsumers);
        } else {
            currentInfrastructureCost = infrastructureCost;
        }
        
        this.price = Math.round(currentInfrastructureCost + productionCost + profit);
    }
    
    public void recalculatePrice(EntityRegister entityRegister) {
        this.changeCosts(this.infrastructureCost, this.productionCost, entityRegister);
    }

    public void addContract(Consumer consumer) {
        Contract contract = new Contract(consumer.getId(), this.price, this.contractLength);
        this.contracts.add(contract);
        consumer.setDistributor(this);
    }

    public void calculateBudget(EntityRegister entityRegister) {
        if (this.contracts.isEmpty()) {
            this.budget -= this.infrastructureCost;
            return;
        }
        
        long revenue = 0;
        for (Contract contract : this.contracts) {
            if (contract.isOnHold()) {
                continue;
            }
            
            revenue += contract.getPrice();
        }

        long costs = this.infrastructureCost + this.productionCost * this.contracts.size();
        this.budget = this.budget + revenue - costs;
    }

    public void removeContract(Consumer consumer) {
        Contract expiredContract = findContract(consumer.getId());
        this.contracts.remove(expiredContract);
        consumer.setDistributor(null);
    }
    
    public Contract findContract(Integer consumerId) {
        for (Contract contract : contracts) {
            if (contract.getConsumerId().equals(consumerId)) {
                return contract;
            }
        }
        
        return null;
    }
}
