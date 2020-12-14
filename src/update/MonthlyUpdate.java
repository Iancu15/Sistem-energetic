package update;

import java.util.List;

import entity.Consumer;

public class MonthlyUpdate {
    private List<Consumer> newConsumers;
    private List<CostChange> costsChanges;
    
    public List<Consumer> getNewConsumers() {
        return newConsumers;
    }
    
    public void setNewConsumers(List<Consumer> newConsumers) {
        this.newConsumers = newConsumers;
    }
    
    public List<CostChange> getCostsChanges() {
        return costsChanges;
    }
    
    public void setCostsChanges(List<CostChange> costsChanges) {
        this.costsChanges = costsChanges;
    }
}
