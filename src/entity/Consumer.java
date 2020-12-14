package entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "isBankrupt", "budget"})
public class Consumer {
    private Integer id;
    private long budget;
    private int monthlyIncome;
    private long debt = 0;
    private boolean isBankrupt = false;
    private Distributor distributor = null;

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @JsonProperty("budget")
    @JsonAlias("initialBudget")
    public long getBudget() {
        return budget;
    }
    
    @JsonProperty("budget")
    @JsonAlias("initialBudget")
    public void setBudget(long budget) {
        this.budget = budget;
    }
    
    @JsonIgnore
    public int getMonthlyIncome() {
        return monthlyIncome;
    }
    
    @JsonProperty
    public void setMonthlyIncome(Integer monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
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
    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }
    
    @JsonIgnore
    public long getDebt() {
        return debt;
    }

    public void setDebt(long debt) {
        this.debt = debt;
    }
}
