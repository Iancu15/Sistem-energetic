package entity;

import java.util.LinkedList;
import java.util.Observable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("deprecation")
@JsonPropertyOrder({"id", "maxDistributors", "priceKW", "energyType", "energyPerDistributor", "monthlyStats"})
public class Producer extends Observable implements Entity {
    private Integer id;
    private EnergyType energyType;
    private int maxDistributors;
    private Float priceKW;
    private Integer energyPerDistributor;
    private LinkedList<MonthlyStat> monthlyStats;
    
    public Producer() {
        this.monthlyStats = new LinkedList<MonthlyStat>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }
    public void setEnergyType(EnergyType energyType) {
        this.energyType = energyType;
    }
    public int getMaxDistributors() {
        return maxDistributors;
    }
    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }
    public Float getPriceKW() {
        return priceKW;
    }
    public void setPriceKW(float priceKW) {
        this.priceKW = priceKW;
    }
    public Integer getEnergyPerDistributor() {
        return energyPerDistributor;
    }
    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }
    
    public LinkedList<MonthlyStat> getMonthlyStats() {
        return monthlyStats;
    }
    public void setMonthlyStats(LinkedList<MonthlyStat> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }
    
    public void notifyDistributors() {
        setChanged();
        notifyObservers();
        clearChanged();
        deleteObservers();
    }
}
