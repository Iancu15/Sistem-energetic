package fileio;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import entity.EntityRegister;
import update.MonthlyUpdate;

public final class Input {
    private int numberOfTurns;
    private EntityRegister entityRegister;
    private List<MonthlyUpdate> monthlyUpdates;

    public Integer getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final Integer numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    @JsonAlias("initialData")
    public EntityRegister getEntityRegister() {
        return entityRegister;
    }

    @JsonAlias("initialData")
    public void setEntityRegister(final EntityRegister entityRegister) {
        this.entityRegister = entityRegister;
    }

    public List<MonthlyUpdate> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(final List<MonthlyUpdate> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }
}
