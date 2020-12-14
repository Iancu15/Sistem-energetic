package update;

import java.util.List;

import entity.Consumer;

/**
 * Contine actualizarile ce trebuie efectuate intr-o anumita tura
 * @author alex
 *
 */
public final class MonthlyUpdate {
    private List<Consumer> newConsumers;
    private List<CostChange> costsChanges;

    public List<Consumer> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<Consumer> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<CostChange> getCostsChanges() {
        return costsChanges;
    }

    public void setCostsChanges(final List<CostChange> costsChanges) {
        this.costsChanges = costsChanges;
    }
}
