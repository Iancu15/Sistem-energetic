package entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("deprecation")
@JsonPropertyOrder({ "id", "maxDistributors", "priceKW", "energyType", "energyPerDistributor", "monthlyStats" })
public final class Producer extends Observable implements Entity {
    private Integer id;
    private EnergyType energyType;
    private int maxDistributors;
    private Float priceKW;
    private Integer energyPerDistributor;
    private LinkedList<MonthlyStat> monthlyStats;
    /**
     * lista cu distribuitorii abonati la producator
     */
    private List<Integer> distributorsIds;

    public Producer() {
        this.distributorsIds = new ArrayList<Integer>();
        this.monthlyStats = new LinkedList<MonthlyStat>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public void setEnergyType(final EnergyType energyType) {
        this.energyType = energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(final int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public Float getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(final float priceKW) {
        this.priceKW = priceKW;
    }

    public Integer getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(final int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public LinkedList<MonthlyStat> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(final LinkedList<MonthlyStat> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    @JsonIgnore
    public List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(final List<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }

    /**
     * Notifica distribuitorii abonati legat de faptul ca si-a schimbat cantitatea
     */
    public void notifyDistributors() {
        setChanged();
        notifyObservers();
        clearChanged();
        deleteObservers();
    }
}
