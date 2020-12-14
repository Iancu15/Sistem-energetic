package entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "isBankrupt", "budget"})
public final class Consumer {
    private Integer id;
    private long budget;
    private int monthlyIncome;
    /**
     * Datoria din luna trecuta
     */
    private long debt = 0;
    /**
     * Evidenta starii de falimentare
     */
    private boolean isBankrupt = false;
    /**
     * Distribuitorul cu care are un contract in momentul de fata
     */
    private Distributor distributor = null;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @JsonProperty("budget")
    @JsonAlias("initialBudget")
    public long getBudget() {
        return budget;
    }

    @JsonProperty("budget")
    @JsonAlias("initialBudget")
    public void setBudget(final long budget) {
        this.budget = budget;
    }

    @JsonIgnore
    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    @JsonProperty
    public void setMonthlyIncome(final Integer monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    @JsonProperty("isBankrupt")
    public boolean isBankrupt() {
        return isBankrupt;
    }

    @JsonProperty("isBankrupt")
    public void setBankrupt(final boolean isBankrupt) {
        this.isBankrupt = isBankrupt;
    }

    @JsonIgnore
    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(final Distributor distributor) {
        this.distributor = distributor;
    }

    @JsonIgnore
    public long getDebt() {
        return debt;
    }

    public void setDebt(final long debt) {
        this.debt = debt;
    }
}
