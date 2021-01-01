package entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "maxDistributors", "priceKW", "energyType", "energyPerDistributor", "monthlyStats"})
public class Producer extends Entity {
    private EnergyType energyType;
    private int maxDistributors;
    private float priceKW;
    private int energyPerDistributor;
    private List<MonthlyStat> monthlyStats;
    
    public Producer() {
        this.monthlyStats = new ArrayList<MonthlyStat>();
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
    public float getPriceKW() {
        return priceKW;
    }
    public void setPriceKW(float priceKW) {
        this.priceKW = priceKW;
    }
    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }
    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }
    
    public List<MonthlyStat> getMonthlyStats() {
        return monthlyStats;
    }
    public void setMonthlyStats(List<MonthlyStat> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }
}
