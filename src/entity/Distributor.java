package entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import strategy.EnergyChoiceStrategyType;
import update.CostChange;

@JsonPropertyOrder({"id", "energyNeededKW", "price", "budget", "producerStrategy", "isBankrupt", "contracts"})
public final class Distributor extends Entity {
    private int contractLength;
    private long budget;
    private int infrastructureCost;
    private int productionCost;
    private int energyNeededKW;
    private EnergyChoiceStrategyType producerStrategy;
    /**
     * Pretul curent cu care se promoveaza distribuitorul
     */
    private long price;
    /**
     * Lista cu contractele curente
     */
    private List<Contract> contracts;
    
    /**
     * Evidenta starii de falimentare
     */
    private boolean isBankrupt = false;
    
    public Distributor() {
        this.contracts = new ArrayList<Contract>();
    }

    @JsonIgnore
    public int getContractLength() {
        return contractLength;
    }

    @JsonProperty
    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    @JsonAlias("initialBudget")
    public long getBudget() {
        return budget;
    }

    @JsonAlias("initialBudget")
    public void setBudget(final int budget) {
        this.budget = budget;
    }

    @JsonIgnore
    @JsonAlias("initialInfrastructureCost")
    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    @JsonProperty
    @JsonAlias("initialInfrastructureCost")
    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }
    
    @JsonIgnore
    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(int productionCost) {
        this.productionCost = productionCost;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(final List<Contract> contracts) {
        this.contracts = contracts;
    }

    @JsonProperty("isBankrupt")
    public boolean isBankrupt() {
        return isBankrupt;
    }

    @JsonProperty("isBankrupt")
    public void setBankrupt(final boolean isBankrupt) {
        this.isBankrupt = isBankrupt;
    }

    @JsonProperty("contractCost")
    public long getPrice() {
        return price;
    }
    
    @JsonIgnore
    public long setPrice() {
        return price;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(EnergyChoiceStrategyType producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    /**
     * Schimba costurile si pretul distribuitorului
     * @param infrastructureCost
     * @param productionCost
     * @param entityRegister
     */
    public void changeCostsOld(final int infrastructureCost, final int productionCost, 
                                                            final EntityRegister entityRegister) {
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
        final long profit = Math.round(Math.floor(0.2 * productionCost));
        if (this.contracts.isEmpty()) {
            this.price = infrastructureCost + productionCost + profit;
            return;
        }

        // numar consumatorii care nu au falimentat tura asta
        int numberOfConsumers = 0;
        List<Consumer> consumers = entityRegister.getConsumers();
        for (final Contract contract : this.contracts) {
            if (contract.isOnHold()) {
                Entity consumer = entityRegister.findEntity(contract.getConsumerId(), consumers);
                if (((Consumer) consumer).isBankrupt()) {
                    continue;
                }
            }

            numberOfConsumers++;
        }

        // calculez costul infrastructurii relative la numarul de consumatori doar in functie
        // de consumatorii nefalimentati
        double currentInfrastructureCost;
        if (numberOfConsumers != 0) {
            currentInfrastructureCost = Math.floor(infrastructureCost / numberOfConsumers);
        } else {
            currentInfrastructureCost = infrastructureCost;
        }

        this.price = Math.round(currentInfrastructureCost + productionCost + profit);
    }

    /**
     * Recalculez pretul in functie de numarul curent de consumatori
     * @param entityRegister
     */
    public void recalculatePrice(final EntityRegister entityRegister) {
        this.changeCostsOld(this.infrastructureCost, this.productionCost, entityRegister);
    }

    /**
     * Adauga un nou contract in lista de contracte
     * @param consumer  Consumatorul cu care se semneaza contractul
     */
    public void addContract(final Consumer consumer) {
        final Contract contract = new Contract(consumer.getId(), this.price, this.contractLength);
        this.contracts.add(contract);
        consumer.setDistributor(this);
    }

    /**
     * Calculeaza noul buget dupa plata costurilor si incasarea ratelor
     * @param entityRegister
     */
    public void calculateBudget() {
        // daca nu are consumatori plateste doar pentru infrastructura
        if (this.contracts.isEmpty()) {
            this.budget -= this.infrastructureCost;
            return;
        }

        // incaseaza ratele de la consumatorii care au platit in tura curenta
        long revenue = 0;
        for (final Contract contract : this.contracts) {
            if (contract.isOnHold()) {
                continue;
            }

            revenue += contract.getPrice();
        }

        final long costs = this.infrastructureCost + this.productionCost * this.contracts.size();
        this.budget = this.budget + revenue - costs;
    }

    /**
     * Sterge un contract din lista de contracte
     * @param consumer  Consumatorul cu care se anuleaza contractul
     */
    public void removeContract(final Consumer consumer) {
        final Contract expiredContract = findContract(consumer.getId());
        this.contracts.remove(expiredContract);
        consumer.setDistributor(null);
    }

    /**
     * Cauta contractul semnat cu un anumit consumator
     * @param consumerId    Id-ul consumatorului dupa care se cauta
     * @return  Contractul aferent consumatorului
     */
    public Contract findContract(final Integer consumerId) {
        for (final Contract contract : contracts) {
            if (contract.getConsumerId().equals(consumerId)) {
                return contract;
            }
        }

        return null;
    }
    
    public void changeInfrastructureCost(CostChange costChange) {
        
    }
}
